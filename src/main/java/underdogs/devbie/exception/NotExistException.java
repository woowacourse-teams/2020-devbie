package underdogs.devbie.exception;

public class NotExistException extends IntervalServerException {

    public NotExistException(String notExistObject) {
        super(String.format("%s 이(가) 존재하지 않습니다.", notExistObject));
    }
}
