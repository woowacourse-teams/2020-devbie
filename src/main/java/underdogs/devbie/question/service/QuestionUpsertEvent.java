package underdogs.devbie.question.service;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import underdogs.devbie.question.domain.Question;

@Getter
public class QuestionUpsertEvent extends ApplicationEvent {

    private final Question question;

    public QuestionUpsertEvent(Object source, Question question) {
        super(source);
        this.question = question;
    }
}
