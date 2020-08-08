package underdogs.devbie.exception;

public class CreateFailException extends IntervalServerException {

    public CreateFailException() {
        super("생성 인자가 올바르지 않습니다.");
    }
}
