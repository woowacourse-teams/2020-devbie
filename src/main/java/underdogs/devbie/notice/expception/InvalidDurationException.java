package underdogs.devbie.notice.expception;

import underdogs.devbie.exception.BadRequestException;

public class InvalidDurationException extends BadRequestException {

    public InvalidDurationException() {
        super("시작일은 종료일 이전이어야 합니다.");
    }
}
