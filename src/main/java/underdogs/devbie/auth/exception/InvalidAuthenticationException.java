package underdogs.devbie.auth.exception;

public class InvalidAuthenticationException extends RuntimeException {

    public InvalidAuthenticationException(String featureName) {
        super(String.format("%s 기능을 요청할 권한이 없습니다.", featureName));
    }
}
