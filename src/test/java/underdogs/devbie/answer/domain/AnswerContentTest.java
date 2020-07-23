package underdogs.devbie.answer.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnswerContentTest {

    private final static String ANSWER_CONTENT = "answer content";

    @DisplayName("AnswerContent - from")
    @Test
    void answerContentInitTest() {
        AnswerContent answerContent = AnswerContent.from(ANSWER_CONTENT);

        assertThat(answerContent.getContent()).isEqualTo(ANSWER_CONTENT);
    }

    @DisplayName("AnswerContent - equals")
    @Test
    void answerContentEqaulsTest() {
        AnswerContent answerContent = AnswerContent.from(ANSWER_CONTENT);
        AnswerContent anotherAnswerContent = AnswerContent.from(ANSWER_CONTENT);

        assertThat(answerContent).isEqualTo(anotherAnswerContent);
    }
}
