package underdogs.devbie.answer.exception;

public class NotMatchedAnswerAuthorException extends RuntimeException {

    public NotMatchedAnswerAuthorException() {
        super("답변 작성자만 수정할 수 있습니다.");
    }
}
