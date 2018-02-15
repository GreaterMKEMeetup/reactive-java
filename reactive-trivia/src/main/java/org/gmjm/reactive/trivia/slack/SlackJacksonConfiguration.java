package org.gmjm.reactive.trivia.slack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class SlackJacksonConfiguration {

    @Bean
    public Module slackModule() {
        return new SlackModule();
    }

    static class SlackModule extends SimpleModule {

        SlackModule() {
            setMixInAnnotation(SlackEvent.class, SlackEventMixin.class);
            setMixInAnnotation(EventPayload.class, EventPayloadMixin.class);
        }

        static abstract class SlackEventMixin {

            @JsonCreator
            SlackEventMixin(@JsonProperty("token") String token, @JsonProperty("team_id") String teamId,
                            @JsonProperty("api_app_id") String appId, @JsonProperty("event") EventPayload eventPayload,
                            @JsonProperty("type") String type, @JsonProperty("authed_users") Set<String> authedUsers,
                            @JsonProperty("event_id") String eventId, @JsonProperty("event_time") Long eventTime) {}
        }

        static abstract class EventPayloadMixin {

            @JsonCreator
            EventPayloadMixin(@JsonProperty("type") EventType eventType) {}

        }



    }

}
