package underdogs.devbie.exception;

public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException() {
        super("이미 존재 합니다.");
    }
}
