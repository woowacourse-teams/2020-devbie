package underdogs.devbie.auth.exception;

import underdogs.devbie.exception.UnAuthorizedException;

public class ExpiredTokenException extends UnAuthorizedException {

    private static final String EXPIRED_TOKEN_MESSAGE = "토큰의 유효 기간이 만료되었습니다.";

    public ExpiredTokenException() {
        super(EXPIRED_TOKEN_MESSAGE);
    }
}
