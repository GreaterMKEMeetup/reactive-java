package org.gmjm.reactive.trivia.slack.web;

import org.gmjm.reactive.trivia.slack.SlackEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/trivia")
public class TriviaController {

    @PostMapping
    public Mono<ServerResponse> handleSlackMessage(@RequestBody ServerRequest request) {
        Mono<SlackEvent> slackEvent = request.bodyToMono(SlackEvent.class);
        return null;
    }

}
