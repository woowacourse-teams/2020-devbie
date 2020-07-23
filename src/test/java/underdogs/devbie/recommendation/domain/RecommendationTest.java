package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecommendationTest {

    @DisplayName("추천 플래그가 같은지 확인")
    @Test
    void isMatchedRecommended() {
        QuestionRecommendation recommendation =
            QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);

        boolean result = recommendation.hasRecommendationTypeOf(RecommendationType.RECOMMENDED);

        assertThat(result).isTrue();
    }

    @DisplayName("추천 플래그 토글")
    @Test
    void toggleIsRecommendedFlag() {
        QuestionRecommendation recommendation =
            QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);

        recommendation.toggleRecommended();

        assertThat(recommendation.getRecommendationType()).isEqualTo(RecommendationType.NON_RECOMMENDED);
    }
}
