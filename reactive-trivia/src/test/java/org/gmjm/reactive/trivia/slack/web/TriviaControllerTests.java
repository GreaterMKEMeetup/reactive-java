package org.gmjm.reactive.trivia.slack.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static org.gmjm.reactive.trivia.slack.JsonSlackTests.slackEventExpected;

/***
 * @WebFluxTest won't work here due to needing to wire Jackson Modules.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
public class TriviaControllerTests {

    @Autowired
    private WebTestClient client;

    @MockBean
    private WebClient webClient;

    @Test
    public void handleSlackMessage_MessageEvent_ShouldHandle() throws Exception {
        client.post().uri("/")
            .syncBody(slackEventExpected)
            .exchange()
            .expectStatus().is2xxSuccessful();
    }

}