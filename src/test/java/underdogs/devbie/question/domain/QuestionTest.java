package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class QuestionTest {

    public static final String TEST_TITLE = "test title";
    public static final String TEST_CONTENT = "test content";

    @DisplayName("Question 빌더 테스트 - title이 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutTitle() {
        assertThatThrownBy(() -> Question.builder()
            .content(TEST_CONTENT)
            .userId(1L)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 빌더 테스트 - content 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutContent() {
        assertThatThrownBy(() -> Question.builder()
            .title(TEST_TITLE)
            .userId(1L)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 빌더 테스트 - author가 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutAuthor() {
        assertThatThrownBy(() -> Question.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 정보 수정 - title, content")
    @Test
    void updateQuestionInfo() {
        Question question = Question.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .userId(1L)
            .build();

        question.updateQuestionInfo("Changed Title", "Changed Content");

        assertAll(
            () -> assertEquals("Changed Title", question.getTitle()),
            () -> assertEquals("Changed Content", question.getContent())
        );
    }

    @DisplayName("조회수 초기값 확인")
    @Test
    void initValueOfVisits() {
        Question question = Question.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .userId(1L)
            .build();

        assertThat(question.getVisits()).isEqualTo(0L);
    }

    @DisplayName("조회수 증가")
    @Test
    void increaseVisits() {
        Question question = Question.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .userId(1L)
            .build();

        question.increaseVisits();

        assertThat(question.getVisits()).isEqualTo(1L);
    }
}