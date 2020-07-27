package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionContentTest {

    public static final String TEST_QUESTION_CONTENT = "Test Content";

    @DisplayName("QuestionContent - from")
    @Test
    void from() {
        QuestionContent questionContent = QuestionContent.from(TEST_QUESTION_CONTENT);

        assertThat(questionContent.getContent()).isEqualTo(TEST_QUESTION_CONTENT);
    }

    @DisplayName("QuestionContent - equals")
    @Test
    void equals() {
        QuestionContent questionContent = QuestionContent.from(TEST_QUESTION_CONTENT);
        QuestionContent anotherQuestionContent = QuestionContent.from(TEST_QUESTION_CONTENT);

        assertThat(questionContent).isEqualTo(anotherQuestionContent);
    }
}
