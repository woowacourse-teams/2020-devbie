package underdogs.devbie.notice.expception;

import underdogs.devbie.exception.NotExistException;

public class NoticeNotFoundException extends NotExistException {

    public NoticeNotFoundException() {
        super("해당 공고를 찾을 수 없습니다.");
    }
}
