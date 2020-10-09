package underdogs.devbie.question.exception;

import exception.ForbiddenException;

public class NotMatchedQuestionAuthorException extends ForbiddenException {

    private static final String MESSAGE = "작성자만 할 수 있습니다.";

    public NotMatchedQuestionAuthorException() {
        super(MESSAGE);
    }
}
