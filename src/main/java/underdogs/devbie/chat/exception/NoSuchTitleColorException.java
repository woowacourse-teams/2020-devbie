package underdogs.devbie.chat.exception;

import exception.NotExistException;

public class NoSuchTitleColorException extends NotExistException {

    private static final String MESSAGE = "존재하지 않는 TitleColor 입니다.";

    public NoSuchTitleColorException() {
        super(MESSAGE);
    }
}
