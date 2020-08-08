package underdogs.devbie.question.exception;

import underdogs.devbie.exception.NotExistException;

public class QuestionNotExistedException extends NotExistException {

    public QuestionNotExistedException() {
        super("존재하지 않는 질문입니다.");
    }
}
