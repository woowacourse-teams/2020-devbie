package underdogs.devbie.recommendation.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.recommendation.dto.RecommendationCountResponse;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

public class RecommendationAcceptanceTest extends AcceptanceTest {

    public static final String RECOMMENDATION_TYPE_FORMAT = "{\"recommendationType\":\"%s\"}";
    public static final String RECOMMENDATION = "RECOMMENDED";
    public static final String NON_RECOMMENDATION = "NON_RECOMMENDED";
    public static final String QUESTION_RECOMMENDATION_URI = "/api/recommendation-question?objectId=";

    @DisplayName("추천 인수 테스트")
    @TestFactory
    Stream<DynamicTest> manageRecommendation() {
        return Stream.of(
            dynamicTest("1번 질문 추천", () -> {
                recommend(1L);

                Long question1Count = recommendationCount(1L).getRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),
            dynamicTest("1번 질문 추천 갯수 확인", () -> {
                String question1Type = searchRecommendation(1L, userId).getRecommendationType();
                assertThat(question1Type).isEqualTo("RECOMMENDED");
            }),
            dynamicTest("2번 질문 비추천", () -> {
                nonRecommend(2L);

                Long question2Count = recommendationCount(2L).getNonRecommendedCount();
                assertThat(question2Count).isEqualTo(1L);
            }),
            dynamicTest("2번 질문 추천 갯수 확인", () -> {
                String question2Type = searchRecommendation(2L, userId).getRecommendationType();
                assertThat(question2Type).isEqualTo("NON_RECOMMENDED");
            }),
            dynamicTest("3번 질문에 아무것도 눌려있지 않다", () -> {
                String question3Type = searchRecommendation(3L, userId).getRecommendationType();
                assertThat(question3Type).isEqualTo("NOT_EXIST");
            }),
            dynamicTest("1번 질문 다시 추천해도 추천수가 증가하지 않는다", () -> {
                recommend(1L);

                Long question1Count = recommendationCount(1L).getRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),

            dynamicTest("1번 질문 비추천", () -> {
                nonRecommend(1L);

                Long question1Count = recommendationCount(1L).getNonRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),
            dynamicTest("2번 질문 추천", () -> {
                recommend(2L);

                Long question2Count = recommendationCount(2L).getRecommendedCount();
                assertThat(question2Count).isEqualTo(1L);
            }),
            dynamicTest("1번 질문 추천 기록 삭제", () -> {
                deleteRecommendation(1L);

                Long question1Count = recommendationCount(1L).getNonRecommendedCount();
                assertThat(question1Count).isEqualTo(0L);
            }), dynamicTest("2번 질문 추천 기록 삭제", () -> {
                deleteRecommendation(2L);

                Long question2Count = recommendationCount(2L).getRecommendedCount();
                assertThat(question2Count).isEqualTo(0L);
            })
        );
    }

    void recommend(Long questionId) {
        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, RECOMMENDATION);
        put(QUESTION_RECOMMENDATION_URI + questionId, inputJson);
    }

    void nonRecommend(Long questionId) {
        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, NON_RECOMMENDATION);
        put(QUESTION_RECOMMENDATION_URI + questionId, inputJson);
    }

    RecommendationResponse searchRecommendation(Long questionId, Long userId) {
        return get(QUESTION_RECOMMENDATION_URI + questionId + "&userId=" + userId, RecommendationResponse.class);
    }

    RecommendationCountResponse recommendationCount(Long questionId) {
        return get(QUESTION_RECOMMENDATION_URI + questionId, RecommendationCountResponse.class);
    }

    void deleteRecommendation(Long questionId) {
        delete(QUESTION_RECOMMENDATION_URI + questionId);
    }
}
