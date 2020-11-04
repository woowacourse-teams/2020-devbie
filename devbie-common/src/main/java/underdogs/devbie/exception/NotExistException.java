package underdogs.devbie.exception;

public class NotExistException extends RuntimeException {

    public NotExistException(String notExistObject) {
        super(notExistObject);
    }
}
