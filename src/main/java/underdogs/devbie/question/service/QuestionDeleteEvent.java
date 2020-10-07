package underdogs.devbie.question.service;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import underdogs.devbie.question.domain.Question;

@Getter
public class QuestionDeleteEvent extends ApplicationEvent {

    private final Question question;

    public QuestionDeleteEvent(Object source, Question question) {
        super(source);
        this.question = question;
    }
}

