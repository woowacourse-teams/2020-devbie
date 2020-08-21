package underdogs.devbie.exception;

public class IntervalServerException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "서버 에러가 발생해 요청이 실패했습니다. 원인 : ";

    public IntervalServerException(String reason) {
        super(MESSAGE_PREFIX + reason);
    }
}
