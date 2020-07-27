package underdogs.devbie.answer.exception;

public class AnswerNotExistedException extends RuntimeException {

    public AnswerNotExistedException() {
        super("존재하지 않는 답변입니다.");
    }
}
