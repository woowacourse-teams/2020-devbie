package underdogs.devbie.chat.exception;

import underdogs.devbie.exception.IntervalServerException;

public class SessionIdAlreadyExistException extends IntervalServerException {

    private static final String MESSAGE = "세션id가 이미 존재합니다";

    public SessionIdAlreadyExistException() {
        super(MESSAGE);
    }
}
