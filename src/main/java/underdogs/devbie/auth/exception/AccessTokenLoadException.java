package underdogs.devbie.auth.exception;

import underdogs.devbie.exception.UnAuthorizedException;

public class AccessTokenLoadException extends UnAuthorizedException {

    private static final String MESSAGE = "토큰을 Load하지 못하였습니다.";

    public AccessTokenLoadException() {
        super(MESSAGE);
    }
}
