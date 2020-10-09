package underdogs.devbie.auth.exception;

import exception.UnAuthorizedException;

public class LoginUserNotFoundException extends UnAuthorizedException {

    private static final String MESSAGE = "토큰에 존재하는 정보와 일치하는 사용자가 존재하지 않습니다.";

    public LoginUserNotFoundException() {
        super(MESSAGE);
    }
}
