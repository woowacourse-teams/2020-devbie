package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;
import underdogs.devbie.recommendation.domain.RecommendationType;

public class QuestionTest {

    public static final QuestionTitle TEST_QUESTION_TITLE = QuestionTitle.from("test title");
    public static final QuestionContent TEST_QUESTION_CONTENT = QuestionContent.from("test content");

    @DisplayName("Question 빌더 테스트 - title이 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutTitle() {
        assertThatThrownBy(() -> Question.builder()
            .userId(1L)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 빌더 테스트 - content 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutContent() {
        assertThatThrownBy(() -> Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .hashtags(new LinkedHashSet<>())
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Question 빌더 테스트 - author가 없을 때 예외 발생")
    @Test
    void questionBuilderWithoutAuthor() {
        assertThatThrownBy(() -> Question.builder()
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
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
            .hashtags(new LinkedHashSet<>())
            .build();
        Question changeQuestion = Question.builder()
            .userId(1L)
            .title(QuestionTitle.from("Changed Title"))
            .content(QuestionContent.from("Changed Content"))
            .hashtags(new LinkedHashSet<>())
            .build();
        question.updateQuestionInfo(changeQuestion);

        assertAll(
            () -> assertThat(question.getTitle()).isEqualTo(changeQuestion.getTitle()),
            () -> assertThat(question.getContent()).isEqualTo(changeQuestion.getContent())
        );
    }

    @DisplayName("조회수 초기값 확인")
    @Test
    void initValueOfVisits() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build();

        assertThat(question.getVisits().getVisitCount()).isEqualTo(0L);
    }

    @DisplayName("조회수 증가")
    @Test
    void increaseVisits() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build();

        question.increaseVisits();

        assertThat(question.getVisits().getVisitCount()).isEqualTo(1L);
    }

    @DisplayName("추천수 증가")
    @Test
    void increaseRecommendationCount() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build();

        question.increaseRecommendationCounts(RecommendationType.RECOMMENDED);

        assertAll(
            () -> assertThat(question.getRecommendationCount().getRecommendedCount()).isEqualTo(1L),
            () -> assertThat(question.getRecommendationCount().getNonRecommendedCount()).isEqualTo(0L)
        );
    }
}
