package org.gmjm.reactive.trivia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ReactiveTrivia {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveTrivia.class, args);
    }

}
