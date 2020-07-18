package underdogs.devbie.auth.exception;

public class InvalidAuthenticationException extends RuntimeException {

    private static final String INVALID_TOKEN_MESSAGE = "유효하지 않는 토큰입니다.";

    public InvalidAuthenticationException() {
        super(INVALID_TOKEN_MESSAGE);
    }
}
