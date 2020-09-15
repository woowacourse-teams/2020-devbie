package underdogs.devbie.chat.exception;

import underdogs.devbie.exception.NotExistException;

public class SessionNotExistException extends NotExistException {

    private static final String MESSAGE = "세션이 존재하지 않습니다";

    public SessionNotExistException() {
        super(MESSAGE);
    }
}
