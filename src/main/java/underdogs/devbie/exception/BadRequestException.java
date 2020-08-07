package underdogs.devbie.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String reason) {
        super("잘못된 요청입니다: 원인 : " + reason);
    }
}
