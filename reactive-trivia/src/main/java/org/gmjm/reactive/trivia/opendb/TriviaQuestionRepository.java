package org.gmjm.reactive.trivia.opendb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TriviaQuestionRepository extends MongoRepository<TriviaQuestion, String> {
}
