package org.gmjm.reactive.trivia.opendb;

import lombok.Value;

import java.util.Set;

@Value
class TriviaQuestion {

    private final String category;
    private final String correctAnswer;
    private final String difficulty;
    private final Set<String> incorrectAnswers;
    private final String question;
    private final String type;

}
