package org.gmjm.reactive.trivia.slack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@Import(SlackJacksonConfiguration.class)
public class JsonSlackTests {

    @Autowired
    private JacksonTester<SlackEvent> slackEventJson;

    @Autowired
    private JacksonTester<EventPayload> eventPayloadJson;

    public static final String VALID_SLACK_MESSAGE_JSON =
        "{" +
            "\"token\":\"XXYYZZ\"," +
            "\"team_id\":\"TXXXXXXXX\"," +
            "\"api_app_id\":\"AXXXXXXXXX\"," +
            "\"event\": {" +
                "\"type\":\"message\"," +
                "\"channel\":\"D024BE91L\"," +
                "\"user\":\"U2147483697\"," +
                "\"text\":\"!trivia Hello world\"," +
                "\"ts\":\"1355517523.000005\"" +
            "}," +
            "\"type\":\"event_callback\"," +
            "\"authed_users\": [" +
                "\"UXXXXXXX1\"," +
                "\"UXXXXXXX2\"" +
            "]," +
            "\"event_id\":\"Ev08MFMKH6\"," +
            "\"event_time\": 1234567890" +
        "}";
    private static EventPayload defaultEventPayload = new EventPayload(EventType.message, new HashMap<String, String>() {{
        put("channel", "D024BE91L");
        put("user", "U2147483697");
        put("text", "Hello world");
        put("ts", "1355517523.000005");
    }});

    public static SlackEvent slackEventExpected = new SlackEvent("XXYYZZ", "TXXXXXXXX", "AXXXXXXXXX",
        defaultEventPayload, "event_callback", new HashSet<String>(Arrays.asList("UXXXXXXX1", "UXXXXXXX2")),
        "Ev08MFMKH6", 1234567890L);

    @Test
    public void deserializeEventPayload() throws Exception {
        String content =
            "{" +
                "\"type\":\"message\"," +
                "\"channel\":\"D024BE91L\"," +
                "\"user\":\"U2147483697\"," +
                "\"text\":\"Hello world\"," +
                "\"ts\":\"1355517523.000005\"" +
            "}";

        assertThat(this.eventPayloadJson.parse(content)).isEqualToComparingFieldByFieldRecursively(defaultEventPayload);
    }

    @Test
    public void deserializeSlackEvent() throws Exception {
        assertThat(slackEventJson.parse(VALID_SLACK_MESSAGE_JSON)).isEqualToIgnoringGivenFields(slackEventExpected, "eventPayload");
    }

}
