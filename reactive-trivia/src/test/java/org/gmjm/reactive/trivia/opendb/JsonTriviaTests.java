package org.gmjm.reactive.trivia.opendb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@Import(TriviaJacksonConfiguration.class)
public class JsonTriviaTests {

    @Autowired
    private JacksonTester<TriviaQuestion> triviaQuestionJacksonTester;

    @Autowired
    private JacksonTester<TriviaQuestionsResponse> triviaQuestionsResponseJacksonTester;

    @Test
    public void triviaQuestionDeserialize() throws Exception {
        TriviaQuestion expected = new TriviaQuestion("Science: Computers", "Shellshock",
            "hard", new HashSet<>(Arrays.asList("Heartbleed", "Bashbug", "Stagefright")),
            "What was the name of the security vulnerability found in Bash in 2014?", "multiple");

        String content =
                "{" +
                    "\"category\":\"Science: Computers\"," +
                    "\"type\":\"multiple\"," +
                    "\"difficulty\":\"hard\"," +
                    "\"question\":\"What was the name of the security vulnerability found in Bash in 2014?\"," +
                    "\"correct_answer\":\"Shellshock\"," +
                    "\"incorrect_answers\": [\"Heartbleed\",\"Bashbug\",\"Stagefright\"]" +
                "}";

        assertThat(this.triviaQuestionJacksonTester.parse(content)).isEqualTo(expected);
    }

    @Test
    public void triviaQuestionsResponseDeserialize() throws Exception {

        TriviaQuestion questions = new TriviaQuestion("Science: Computers", "Shellshock",
            "hard", new HashSet<>(Arrays.asList("Heartbleed", "Bashbug", "Stagefright")),
            "What was the name of the security vulnerability found in Bash in 2014?", "multiple");

        TriviaQuestionsResponse expected = new TriviaQuestionsResponse(0, new HashSet<>(Arrays.asList(questions)));

        String content =
                "{" +
                    "\"response_code\": 0," +
                    "\"results\": [" +
                        "{" +
                            "\"category\":\"Science: Computers\"," +
                            "\"type\":\"multiple\"," +
                            "\"difficulty\":\"hard\"," +
                            "\"question\":\"What was the name of the security vulnerability found in Bash in 2014?\"," +
                            "\"correct_answer\":\"Shellshock\"," +
                            "\"incorrect_answers\": [\"Heartbleed\",\"Bashbug\",\"Stagefright\"]" +
                        "}" +
                    "]" +
                "}";

        assertThat(this.triviaQuestionsResponseJacksonTester.parse(content)).isEqualTo(expected);
    }

}
