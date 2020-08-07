package underdogs.devbie.user.exception;

public class NotMatchedUserException extends RuntimeException {
    public NotMatchedUserException() {
        super("본인만 수정할 수 있습니다.");
    }
}

