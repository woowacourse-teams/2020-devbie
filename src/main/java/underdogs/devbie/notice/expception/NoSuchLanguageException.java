package underdogs.devbie.notice.expception;

import java.util.NoSuchElementException;

public class NoSuchLanguageException extends NoSuchElementException {
    public NoSuchLanguageException() {
        super("존재하지 않는 프로그래밍 언어 입니다.");
    }
}
