package underdogs.devbie.notice.expception;

import underdogs.devbie.exception.NotExistException;

public class NoSuchLanguageException extends NotExistException {

    public NoSuchLanguageException() {
        super("존재하지 않는 프로그래밍 언어 입니다.");
    }
}
