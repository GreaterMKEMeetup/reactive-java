package org.gmjm.reactive.trivia.slack;

import lombok.extern.apachecommons.CommonsLog;
import org.gmjm.reactive.trivia.opendb.TriviaQuestion;
import org.gmjm.reactive.trivia.opendb.TriviaQuestionsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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

    @Scheduled(fixedRate = 1000 * 60 * 2)
    public void postQuestion() {
        openTriviaApiWebClient
            .get()
            .retrieve()
            .bodyToMono(TriviaQuestionsResponse.class)
            .doOnNext(log::info)
            .map(res -> res.getTriviaQuestions().iterator().next())
            .map(TriviaQuestion::toSlackMessage);
    }

}
