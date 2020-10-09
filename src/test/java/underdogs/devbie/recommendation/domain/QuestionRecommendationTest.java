package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.CreateFailException;

class QuestionRecommendationTest {

    @DisplayName("QuestionRecommendation 생성 테스트 - userId가 없을 때 예외 발생")
    @Test
    void recommendationInitializeWithoutUserId() {
        assertThatThrownBy(() ->
            QuestionRecommendation.of(1L, null, RecommendationType.RECOMMENDED)
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("QuestionRecommendation 생성 테스트 - recommendationType 없을 때 예외 발생")
    @Test
    void recommendationInitializeWithoutRecommendationType() {
        assertThatThrownBy(() ->
            QuestionRecommendation.of(1L, 1L, null)
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("QuestionRecommendation 생성 테스트 - questionId가 없을 때 예외 발생")
    @Test
    void questionRecommendationInitializeWithoutQuestionId() {
        assertThatThrownBy(() ->
            QuestionRecommendation.of(null, 1L, RecommendationType.RECOMMENDED)
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("QuestionRecommendation 생성 테스트")
    @Test
    void recommendationInitialize() {
        assertThat(QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED))
            .isInstanceOf(QuestionRecommendation.class);
    }
}
