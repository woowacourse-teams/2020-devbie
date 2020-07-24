package underdogs.devbie.notice.expception;

import java.util.NoSuchElementException;

public class NoticeNotFoundException extends NoSuchElementException {
    public NoticeNotFoundException() {
        super("해당 공고를 찾을 수 없습니다.");
    }
}
