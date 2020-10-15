package underdogs.devbie.auth.exception;

import underdogs.devbie.exception.UnAuthorizedException;

public class NotExistUserRoleException extends UnAuthorizedException {

    private static final String message = "존재하지 않는 UserRole 입니다.";

    public NotExistUserRoleException() {
        super(message);
    }
}
