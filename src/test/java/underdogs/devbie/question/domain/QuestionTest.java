package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class QuestionTest {

    public static final QuestionTitle TEST_QUESTION_TITLE = QuestionTitle.from("test title");
    public static final QuestionContent TEST_QUESTION_CONTENT = QuestionContent.from("test content");

    @DisplayName("Question 빌더 테스트 - title이 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutTitle() {
        assertThatThrownBy(() -> Question.builder()
            .userId(1L)
            .content(TEST_QUESTION_CONTENT)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 빌더 테스트 - content 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutContent() {
        assertThatThrownBy(() -> Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 빌더 테스트 - author가 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutAuthor() {
        assertThatThrownBy(() -> Question.builder()
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 정보 수정 - title, content")
    @Test
    void updateQuestionInfo() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();

        QuestionTitle changedQuestionTitle = QuestionTitle.from("Changed Title");
        QuestionContent changedQuestionContent = QuestionContent.from("Changed Content");
        question.updateQuestionInfo(changedQuestionTitle, changedQuestionContent);

        assertAll(
            () -> assertEquals(changedQuestionTitle, question.getTitle()),
            () -> assertEquals(changedQuestionContent, question.getContent())
        );
    }

    @DisplayName("조회수 초기값 확인")
    @Test
    void initValueOfVisits() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();

        assertThat(question.getVisits().getValue()).isEqualTo(0L);
    }

    @DisplayName("조회수 증가")
    @Test
    void increaseVisits() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();

        question.increaseVisits();

        assertThat(question.getVisits().getValue()).isEqualTo(1L);
    }
}