package underdogs.devbie.question.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import underdogs.devbie.question.domain.EsQuestion;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class EsSynchronizer implements ApplicationRunner {

    private final QuestionRepository questionRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        log.info("Start syncing - {}", LocalDateTime.now());
        this.syncQuestions();
        log.info("End syncing - {}", LocalDateTime.now());
    }

    private void syncQuestions() {
        if (!elasticsearchOperations.indexOps(EsQuestion.class).exists()) {
            List<Question> questionList = questionRepository.findAll();
            for (Question question : questionList) {
                log.info("Syncing Question - {}", question.getId());
                elasticsearchOperations.save(EsQuestion.from(question));
            }
        }
    }
}
