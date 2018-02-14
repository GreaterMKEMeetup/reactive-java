package org.gmjm.reactive.trivia.slack;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class EventPayload {

    private final EventType eventType;
    private Map<String, String> fields = new HashMap<>();

    public EventPayload(EventType eventType, Map<String, String> fields) {
        this.eventType = eventType;
        this.fields = fields;
    }

    /***
     *
     * Cheating a bit here to allow for generic JSON.
     */
    @JsonAnySetter
    public void addField(String key, String value) {
        this.fields.put(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
