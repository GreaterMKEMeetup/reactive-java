package org.gmjm.reactive.trivia.slack;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(value = "schedule.trivia.questions", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableScheduling
public class ScheduleConfiguration {
}
