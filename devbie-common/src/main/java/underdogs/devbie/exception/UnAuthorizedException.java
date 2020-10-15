package underdogs.devbie.exception;

public class UnAuthorizedException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "요청자의 신분을 확인하지 못하였습니다. 원인 : ";

    public UnAuthorizedException(String reason) {
        super(MESSAGE_PREFIX + reason);
    }
}
