package underdogs.devbie.question.exception;

public class HashtagNotExistedException extends RuntimeException {

    public HashtagNotExistedException() {
        super("존재하지 않는 해시태그입니다.");
    }
}
