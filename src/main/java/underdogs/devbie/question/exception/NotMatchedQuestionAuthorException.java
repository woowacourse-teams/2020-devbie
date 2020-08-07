package underdogs.devbie.question.exception;

import underdogs.devbie.exception.ForbiddenException;

public class NotMatchedQuestionAuthorException extends ForbiddenException {

    public NotMatchedQuestionAuthorException() {
        super("작성자만 할 수 있습니다.");
    }
}
