package underdogs.devbie.notice.expception;

public class InvalidDurationException extends IllegalArgumentException {

    public InvalidDurationException() {
        super("시작일은 종료일 이전이어야 합니다.");
    }
}
