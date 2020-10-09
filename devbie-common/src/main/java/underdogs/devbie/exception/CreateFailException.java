package underdogs.devbie.exception;

public class CreateFailException extends RuntimeException {

    private static final String MESSAGE = "생성 인자가 올바르지 않습니다.";

    public CreateFailException() {
        super(MESSAGE);
    }
}
