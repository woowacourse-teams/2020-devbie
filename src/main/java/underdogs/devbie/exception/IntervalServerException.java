package underdogs.devbie.exception;

public class IntervalServerException extends RuntimeException {

    public IntervalServerException(String reason) {
        super("서버 에러가 발생해 요청이 실패했습니다. 원인 : " + reason);
    }
}
