package underdogs.devbie.question.exception;

import exception.NotExistException;

public class QuestionNotExistedException extends NotExistException {

    private static final String MESSAGE = "존재하지 않는 질문입니다.";

    public QuestionNotExistedException() {
        super(MESSAGE);
    }
}
