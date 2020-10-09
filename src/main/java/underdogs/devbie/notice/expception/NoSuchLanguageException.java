package underdogs.devbie.notice.expception;

import exception.NotExistException;

public class NoSuchLanguageException extends NotExistException {

    private static final String MESSAGE = "존재하지 않는 프로그래밍 언어 입니다.";

    public NoSuchLanguageException() {
        super(MESSAGE);
    }
}
