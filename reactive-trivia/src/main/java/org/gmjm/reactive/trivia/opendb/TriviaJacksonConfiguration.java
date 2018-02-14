package org.gmjm.reactive.trivia.opendb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
class TriviaJacksonConfiguration {

    @Bean
    Module triviaModule() {
        return new TriviaModule();
    }

    static class TriviaModule extends SimpleModule {

        TriviaModule() {
            setMixInAnnotation(TriviaQuestion.class, TriviaQuestionMixin.class);
            setMixInAnnotation(TriviaQuestionsResponse.class, TriviaQuestionsResponseMixin.class);
        }

        static abstract class TriviaQuestionMixin {

            @JsonCreator
            public TriviaQuestionMixin(@JsonProperty("category") String category, @JsonProperty("correct_answer") String correctAnswer,
                                       @JsonProperty("difficulty") String difficulty, @JsonProperty("incorrect_answers") Set<String> incorrectAnswers,
                                       @JsonProperty("question") String question, @JsonProperty("type") String type) {}

        }

        static abstract class TriviaQuestionsResponseMixin {

            @JsonCreator
            public TriviaQuestionsResponseMixin(@JsonProperty("response_code") int responseCode,
                                                @JsonProperty("results") Set<TriviaQuestion> triviaQuestions) {}

        }

    }

}
