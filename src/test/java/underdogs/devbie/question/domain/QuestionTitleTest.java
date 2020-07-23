package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionTitleTest {

    private static final String TEST_QUESTION_TITLE = "Test Question Title";

    @DisplayName("Question Title - from")
    @Test
    void from() {
        QuestionTitle questionTitle = QuestionTitle.from(TEST_QUESTION_TITLE);

        assertThat(questionTitle.getTitle()).isEqualTo(TEST_QUESTION_TITLE);
    }

    @DisplayName("Question Title - eqauls")
    @Test
    void equals() {
        QuestionTitle questionTitle = QuestionTitle.from(TEST_QUESTION_TITLE);
        QuestionTitle anotherQuestionTitle = QuestionTitle.from(TEST_QUESTION_TITLE);

        assertThat(questionTitle).isEqualTo(anotherQuestionTitle);
    }
}
