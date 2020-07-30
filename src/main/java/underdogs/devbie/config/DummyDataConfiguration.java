package underdogs.devbie.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;
import underdogs.devbie.answer.domain.repository.AnswerRepository;

@Configuration
public class DummyDataConfiguration {
    @Configuration
    private class LocalDataApplicationRunner implements ApplicationRunner {

        private final AnswerRepository answerRepository;

        public LocalDataApplicationRunner(AnswerRepository answerRepository) {
            this.answerRepository = answerRepository;
        }

        @Override
        public void run(ApplicationArguments args) {

            Answer ans1 = Answer.builder()
                .userId(1L)
                .questionId(1L)
                .content(AnswerContent.from(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
                .build();
            Answer ans2 = Answer.builder()
                .userId(1L)
                .questionId(1L)
                .content(AnswerContent.from("content2"))
                .build();
            Answer ans3 = Answer.builder()
                .userId(1L)
                .questionId(1L)
                .content(AnswerContent.from("content3"))
                .build();
            Answer ans4 = Answer.builder()
                .userId(1L)
                .questionId(1L)
                .content(AnswerContent.from("content4"))
                .build();
            Answer ans5 = Answer.builder()
                .userId(1L)
                .questionId(1L)
                .content(AnswerContent.from("content5"))
                .build();

            answerRepository.save(ans1);
            answerRepository.save(ans2);
            answerRepository.save(ans3);
            answerRepository.save(ans4);
            answerRepository.save(ans5);
        }
    }
}
