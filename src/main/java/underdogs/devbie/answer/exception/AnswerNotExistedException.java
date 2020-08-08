package underdogs.devbie.answer.exception;

import underdogs.devbie.exception.NotExistException;

public class AnswerNotExistedException extends NotExistException {

    public AnswerNotExistedException() {
        super("답변이 존재하지 않습니다.");
    }
}
