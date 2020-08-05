package underdogs.devbie.auth.exception;

import underdogs.devbie.exception.AuthException;

public class InvalidAuthenticationException extends AuthException {

    public InvalidAuthenticationException(String reason) {
        super(reason);
    }
}
