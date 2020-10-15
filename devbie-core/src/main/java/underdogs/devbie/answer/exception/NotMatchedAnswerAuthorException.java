package underdogs.devbie.answer.exception;

public class NotMatchedAnswerAuthorException extends InvalidAuthenticationException {

    private static final String FEATURE_NAME = "답변 작성 수정";

    public NotMatchedAnswerAuthorException() {
        super(FEATURE_NAME);
    }
}
