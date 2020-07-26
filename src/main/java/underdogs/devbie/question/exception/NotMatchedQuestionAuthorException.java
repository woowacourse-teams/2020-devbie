package underdogs.devbie.question.exception;

public class NotMatchedQuestionAuthorException extends RuntimeException {
    public NotMatchedQuestionAuthorException() {
        super("작성자만 할 수 있습니다.");
    }
}
