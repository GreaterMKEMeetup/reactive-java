package org.gmjm.reactive.trivia.slack;

import lombok.Value;

import java.util.Set;

@Value
public class SlackEvent {

    private final String token;
    private final String teamId;
    private final String appId;
    private final EventPayload eventPayload;
    private final String type;
    private final Set<String> authedUsers;
    private final String eventId;
    private final Long eventTime;

}
