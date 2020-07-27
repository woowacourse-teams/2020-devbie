package underdogs.devbie.question.exception;

public class QuestionNotExistedException extends RuntimeException {
    public QuestionNotExistedException() {
        super("존재하지 않는 질문입니다.");
    }
}
