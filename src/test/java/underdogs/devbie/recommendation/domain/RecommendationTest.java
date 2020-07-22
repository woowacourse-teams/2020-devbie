package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class RecommendationTest {

    @DisplayName("Recommendation 빌더 테스트 - userId가 없을 때 예외 발생")
    @Test
    void recommendationBuilderWithoutUserId() {
        assertThatThrownBy(() ->
            QuestionRecommendation.builder()
                .questionId(1L)
                .recommendationType(RecommendationType.RECOMMENDED)
                .build()
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Recommendation 빌더 테스트 - recommendationType 없을 때 예외 발생")
    @Test
    void recommendationBuilderWithoutRecommendationType() {
        assertThatThrownBy(() ->
            QuestionRecommendation.builder()
                .userId(1L)
                .questionId(1L)
                .build()
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("추천 플래그가 같은지 확인")
    @Test
    void isMatchedRecommended() {
        QuestionRecommendation recommendation = QuestionRecommendation.builder()
            .questionId(1L)
            .userId(1L)
            .recommendationType(RecommendationType.RECOMMENDED)
            .build();

        boolean result = recommendation.hasRecommendationTypeOf(RecommendationType.RECOMMENDED);

        assertThat(result).isTrue();
    }

    @DisplayName("추천 플래그 토글")
    @Test
    void toggleIsRecommendedFlag() {
        QuestionRecommendation recommendation = QuestionRecommendation.builder()
            .questionId(1L)
            .userId(1L)
            .recommendationType(RecommendationType.RECOMMENDED)
            .build();

        recommendation.toggleRecommended();

        assertThat(recommendation.getRecommendationType()).isEqualTo(RecommendationType.NON_RECOMMENDED);
    }
}