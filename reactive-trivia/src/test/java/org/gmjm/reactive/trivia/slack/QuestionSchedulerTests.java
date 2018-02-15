package org.gmjm.reactive.trivia.slack;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(properties = "schedule.trivia.questions=false") // see: https://stackoverflow.com/a/42597467/717932
public class QuestionSchedulerTests {

    @Autowired
    private QuestionScheduler questionScheduler;

}
