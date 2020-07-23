package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class AnswerRecommendationTest {

    @DisplayName("AnswerRecommendation 빌더 테스트 - userId가 없을 때 예외 발생")
    @Test
    void recommendationBuilderWithoutUserId() {
        assertThatThrownBy(() ->
            AnswerRecommendation.builder()
                .answerId(1L)
                .recommendationType(RecommendationType.RECOMMENDED)
                .build()
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("AnswerRecommendation 빌더 테스트 - recommendationType 없을 때 예외 발생")
    @Test
    void recommendationBuilderWithoutRecommendationType() {
        assertThatThrownBy(() ->
            AnswerRecommendation.builder()
                .userId(1L)
                .answerId(1L)
                .build()
        ).isInstanceOf(CreateFailException.class);
    }

    @DisplayName("AnswerRecommendation 빌더 테스트 - answerId가 없을 때 예외 발생")
    @Test
    void answerRecommendationBuilderWithoutAnswerId() {
        assertThatThrownBy(() ->
            AnswerRecommendation.builder()
                .userId(1L)
                .recommendationType(RecommendationType.RECOMMENDED)
                .build()
        ).isInstanceOf(CreateFailException.class);
    }
}