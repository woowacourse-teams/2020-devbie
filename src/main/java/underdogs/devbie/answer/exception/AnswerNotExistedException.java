package underdogs.devbie.answer.exception;

import underdogs.devbie.exception.NotExistException;

public class AnswerNotExistedException extends NotExistException {

    private static final String MESSAGE = "답변이 존재하지 않습니다.";

    public AnswerNotExistedException() {
        super(MESSAGE);
    }
}
