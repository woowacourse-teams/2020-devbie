package underdogs.devbie.question.service.event;

import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.service.HashtagService;

@Component
@RequiredArgsConstructor
public class QuestionDeleteListener {

    private final HashtagService hashtagService;

    @Async("threadPoolTaskExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT, fallbackExecution = true)
    public void handleQuestionDelete(QuestionDeleteEvent event) {
        hashtagService.deleteEmptyRefHashtag(event.getQuestionHashtags());
    }
}
