package underdogs.devbie.question.service;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.EsQuestion;

@Component
@RequiredArgsConstructor
public class QuestionListener {

    private final ElasticsearchOperations elasticsearchOperations;

    @Async("threadPoolTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void syncUpsertElasticSearch(QuestionUpsertEvent event) {
        elasticsearchOperations.save(EsQuestion.from(event.getQuestion()));
    }

    @Async("threadPoolTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void syncDeleteElasticSearch(QuestionDeleteEvent event) {
        elasticsearchOperations.delete(EsQuestion.from(event.getQuestion()));
    }
}
