package underdogs.devbie.answer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import underdogs.devbie.exception.CreateFailException;
import underdogs.devbie.question.domain.Question;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    public static final String TEST_ANSWER_CONTENT = "test answer content";

    @DisplayName("Answer 빌더 테스트 - userId 없을 때 예외 발생")
    @Test
    void answerBuilderWithoutUserId() {
        assertThatThrownBy(() -> Answer.builder()
            .questionId(1L)
            .content(TEST_ANSWER_CONTENT)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Answer 빌더 테스트 - questionId 없을 때 예외 발생")
    @Test
    void answerBuilderWithoutQuestionId() {
        assertThatThrownBy(() -> Answer.builder()
                .userId(1L)
                .content(TEST_ANSWER_CONTENT)
                .build())
                .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Answer 빌더 테스트 - questionId 없을 때 예외 발생")
    @Test
    void answerBuilderWithoutContent() {
        assertThatThrownBy(() -> Answer.builder()
            .userId(1L)
            .questionId(1L)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Answer 정보 수정 - content")
    @Test
    void updateContent() {
        Answer answer = Answer.builder()
            .userId(1L)
            .questionId(1L)
            .content(TEST_ANSWER_CONTENT)
            .build();

        answer.updateContent("Changed Content");

        assertThat(answer.getContent()).isEqualTo("Changed Content");
    }
}