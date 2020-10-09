package exception;

public class NotExistException extends RuntimeException {

    public NotExistException(String notExistObject) {
        super(notExistObject);
    }
}
