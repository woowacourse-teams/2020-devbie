package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class AnswerRecommendationTest {

    @DisplayName("AnswerRecommendation 생성 테스트 - userId가 없을 때 예외 발생")
    @Test
    void recommendationInitializeWithoutUserId() {
        assertThatThrownBy(() ->
            AnswerRecommendation.of(1L, null, RecommendationType.RECOMMENDED)
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("AnswerRecommendation 생성 테스트 - recommendationType 없을 때 예외 발생")
    @Test
    void recommendationInitializeWithoutRecommendationType() {
        assertThatThrownBy(() ->
            AnswerRecommendation.of(1L, 1L, null)
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("AnswerRecommendation 생성 테스트 - answerId가 없을 때 예외 발생")
    @Test
    void recommendationInitializeWithoutAnswerId() {
        assertThatThrownBy(() ->
            AnswerRecommendation.of(null, 1L, RecommendationType.RECOMMENDED)
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("AnswerRecommendation 생성 테스트")
    @Test
    void recommendationInitialize() {
        assertThat(AnswerRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED))
            .isInstanceOf(AnswerRecommendation.class);
    }
}