package underdogs.devbie.chat.exception;

public class SessionIdAlreadyExistException extends RuntimeException {

    private static final String MESSAGE = "세션id가 이미 존재합니다";

    public SessionIdAlreadyExistException() {
        super(MESSAGE);
    }
}
