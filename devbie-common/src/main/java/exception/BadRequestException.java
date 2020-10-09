package exception;

public class BadRequestException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "잘못된 요청입니다: 원인 : ";

    public BadRequestException(String reason) {
        super(MESSAGE_PREFIX + reason);
    }
}
