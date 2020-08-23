package underdogs.devbie.auth.exception;

import underdogs.devbie.exception.ForbiddenException;

public class InvalidAuthenticationException extends ForbiddenException {

    public InvalidAuthenticationException(String reason) {
        super(reason);
    }
}
