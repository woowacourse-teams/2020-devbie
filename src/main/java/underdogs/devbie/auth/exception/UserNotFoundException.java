package underdogs.devbie.auth.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("비정상적인 로그인");
    }
}
