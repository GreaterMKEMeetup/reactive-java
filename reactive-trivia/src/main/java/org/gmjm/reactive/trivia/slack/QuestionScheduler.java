package org.gmjm.reactive.trivia.slack;

import lombok.extern.apachecommons.CommonsLog;
import org.gmjm.reactive.trivia.opendb.TriviaQuestion;
import org.gmjm.reactive.trivia.opendb.TriviaQuestionsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CommonsLog
@Component
public class QuestionScheduler {

    private final WebClient slackWebHookWebClient;
    private final WebClient openTriviaApiWebClient;

    public QuestionScheduler(@Value("${slack.webhook.url}") String slackWebHookUrl,
                             @Value("${open.trivia.api.url}") String triviaApiUrl) {
        this.slackWebHookWebClient = WebClient.create(slackWebHookUrl);
        this.openTriviaApiWebClient = WebClient.create(triviaApiUrl);
    }

    /**
     * Naive approach, if multiple instances of application, it will fire multiple times.
     *
     * In a multi-instance environment you'd need to check for leader, or schedule differently.
     */
    @Scheduled(fixedRateString = "${trivia.question.rate}")
    public void postQuestion() {
        openTriviaApiWebClient
            .get()
            .retrieve()
            .bodyToMono(TriviaQuestionsResponse.class)
            .doOnNext(log::debug)
            .flatMapMany(res -> Flux.fromStream(res.getTriviaQuestions().stream()))
            .take(1)
            .map(TriviaQuestion::toSlackMessage)
            .doOnNext(map ->
                slackWebHookWebClient
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .syncBody(map)
                    .exchange()
            )
            .then();
    }

}
