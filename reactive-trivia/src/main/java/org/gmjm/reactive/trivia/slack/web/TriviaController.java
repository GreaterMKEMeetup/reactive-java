package org.gmjm.reactive.trivia.slack.web;

import lombok.extern.apachecommons.CommonsLog;
import org.gmjm.reactive.trivia.slack.SlackEvent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CommonsLog
@RestController
@RequestMapping("/")
public class TriviaController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> handleSlackMessage(@RequestBody Mono<SlackEvent> slackEvent) {
        return slackEvent
            .filter(e -> e.getEventPayload().getEventType() != null)
            .filter(SlackEvent::isTriviaMessage)
            .map(SlackEvent::toChatMessage)
            .doOnNext(log::info)
            .then();
    }

}
