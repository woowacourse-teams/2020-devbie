package underdogs.devbie.notice.expception;

import underdogs.devbie.exception.BadRequestException;

public class InvalidDurationException extends BadRequestException {

    private static final String MESSAGE = "시작일은 종료일 이전이어야 합니다.";

    public InvalidDurationException() {
        super(MESSAGE);
    }
}
