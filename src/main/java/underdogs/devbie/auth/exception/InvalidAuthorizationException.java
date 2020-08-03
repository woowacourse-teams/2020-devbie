package underdogs.devbie.auth.exception;

public class InvalidAuthorizationException extends RuntimeException {

    private static final String INVALID_AUTHORIZATION_MESSAGE = "권한없는 사용자입니다.";

    public InvalidAuthorizationException() {
        super(INVALID_AUTHORIZATION_MESSAGE);
    }
}
