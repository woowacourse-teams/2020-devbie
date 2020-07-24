package underdogs.devbie.question.exception;

public class NotMatchedAuthorException extends RuntimeException {
    public NotMatchedAuthorException() {
        super("작성자만 할 수 있습니다.");
    }
}
