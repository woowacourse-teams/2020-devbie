package underdogs.devbie.answer.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class AnswerTest {

    public static final AnswerContent TEST_ANSWER_CONTENT = AnswerContent.from("test answer content");

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

        AnswerContent changedContent = AnswerContent.from("Changed Content");
        answer.updateContent(changedContent);

        assertThat(answer.getContent()).isEqualTo(changedContent);
    }

    @DisplayName("Answer userId 비교")
    @Test
    void isMatched() {
        Answer answer = Answer.builder()
            .userId(1L)
            .questionId(1L)
            .content(TEST_ANSWER_CONTENT)
            .build();

        assertThat(answer.isNotMatched(1L)).isFalse();
    }
}
