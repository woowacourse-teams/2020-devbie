package underdogs.devbie.exception;

public class ForbiddenException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "요청을 수행할 권리가 없습니다. 원: ";

    public ForbiddenException(String reason) {
        super(MESSAGE_PREFIX + reason);
    }
}
