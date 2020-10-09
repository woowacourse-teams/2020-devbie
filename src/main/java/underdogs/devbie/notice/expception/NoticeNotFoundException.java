package underdogs.devbie.notice.expception;

import exception.NotExistException;

public class NoticeNotFoundException extends NotExistException {

    private static final String MESSAGE = "해당 공고를 찾을 수 없습니다.";

    public NoticeNotFoundException() {
        super(MESSAGE);
    }
}
