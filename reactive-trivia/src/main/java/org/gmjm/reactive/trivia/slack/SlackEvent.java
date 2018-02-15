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

    public ChatMessage toChatMessage() {
        String user = eventPayload.getFields().get("user");
        String text = eventPayload.getFields().get("text");

        return new ChatMessage(user, text, eventTime);
    }

    public boolean isTriviaMessage() {
        String text = eventPayload.getFields().get("text");
        return text.startsWith("!trivia");
    }

    @Value
    public static class ChatMessage {

        private final String user;
        private final String text;
        private final Long eventTime;

    }
}
