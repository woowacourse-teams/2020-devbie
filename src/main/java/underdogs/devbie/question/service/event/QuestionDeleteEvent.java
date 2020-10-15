package underdogs.devbie.question.service.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import underdogs.devbie.question.domain.QuestionHashtags;

@Getter
public class QuestionDeleteEvent extends ApplicationEvent {

    private final QuestionHashtags questionHashtags;

    public QuestionDeleteEvent(Object source, QuestionHashtags questionHashtags) {
        super(source);
        this.questionHashtags = questionHashtags;
    }
}
