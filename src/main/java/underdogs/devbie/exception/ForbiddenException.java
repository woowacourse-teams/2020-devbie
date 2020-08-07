package underdogs.devbie.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String reason) {
        super("요청을 수행할 권리가 없습니다. 원일: " + reason);
    }
}
