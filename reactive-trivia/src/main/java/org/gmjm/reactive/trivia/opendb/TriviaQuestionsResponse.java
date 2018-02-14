package org.gmjm.reactive.trivia.opendb;

import lombok.Value;

import java.util.Set;

@Value
class TriviaQuestionsResponse {

    private final int code;
    private final Set<TriviaQuestion> triviaQuestions;

}
