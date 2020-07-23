package underdogs.devbie.notice.expception;

public class CreateFailException extends RuntimeException {
    public CreateFailException() {
        super("생성 인자가 올바르지 않습니다.");
    }
}
