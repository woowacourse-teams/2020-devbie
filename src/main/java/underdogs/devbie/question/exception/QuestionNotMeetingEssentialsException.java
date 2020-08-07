package underdogs.devbie.question.exception;

import underdogs.devbie.exception.BadRequestException;

public class QuestionNotMeetingEssentialsException extends BadRequestException {

    public QuestionNotMeetingEssentialsException(String reason) {
        super(reason);
    }
}
