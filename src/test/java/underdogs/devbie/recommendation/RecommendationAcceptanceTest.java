package underdogs.devbie.recommendation;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@ExtendWith(MockitoExtension.class)
public class RecommendationAcceptanceTest extends AcceptanceTest {

    public static final String RECOMMENDATION_TYPE_FORMAT = "{\"recommendationType\":\"%s\"}";
    public static final String RECOMMENDATION = "RECOMMENDED";
    public static final String NON_RECOMMENDATION = "NON_RECOMMENDED";
    public static final String QUESTION_RECOMMENDATION_URI = "/api/recommendation-question/";

    @DisplayName("추천 인수 테스트")
    @TestFactory
    Stream<DynamicTest> manageRecommendation() {
        return Stream.of(
            DynamicTest.dynamicTest("1번 질문 추천", () -> {
                // 1번 질문을 추천 한다
                recommend(1L);

                // 1번 질문에 추천 수가 1이다
                Long question1Count = recommendationCount(1L).getRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),
            DynamicTest.dynamicTest("2번 질문 비추천", () -> {
                // 2번 질문을 비추천 한다
                nonRecommend(2L);

                // 2번 질문에 비추천 수가 1이다
                Long question2Count = recommendationCount(2L).getNonRecommendedCount();
                assertThat(question2Count).isEqualTo(1L);
            }),
            DynamicTest.dynamicTest("1번 질문 비추천", () -> {
                // 1번 질문을 비추천으로 변경한다
                toggleToNonRecommended(1L);

                // 1번 질문에 비추천 수가 1이다
                Long question1Count = recommendationCount(1L).getNonRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),
            DynamicTest.dynamicTest("2번 질문 추천", () -> {
                // 2번 질문을 추천으로 변경한다
                toggleToRecommended(2L);

                // 2번 질문에 추천 수가 1이다
                Long question2Count = recommendationCount(2L).getRecommendedCount();
                assertThat(question2Count).isEqualTo(1L);
            }),
            DynamicTest.dynamicTest("1번 질문 추천 기록 삭제", () -> {
                // 1번 질문의 추천 기록을 삭제한다
                deleteRecommendation(1L);

                // 1번 질문에 비추천 수가 0이다
                Long question1Count = recommendationCount(1L).getNonRecommendedCount();
                assertThat(question1Count).isEqualTo(0L);
            }), DynamicTest.dynamicTest("2번 질문 추천 기록 삭제", () -> {
                // 2번 질문의 추천 기록을 삭제한다
                deleteRecommendation(2L);

                // 2번 질문에 추천 수가 0이다
                Long question2Count = recommendationCount(2L).getRecommendedCount();
                assertThat(question2Count).isEqualTo(0L);
            })

        );
        // 이미 추천하거나 비추한 질문에 대한 postFail이 존재하지 않아서 인수테스트에서 확인 x
    }

    void recommend(Long questionId) {
        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, RECOMMENDATION);
        post(QUESTION_RECOMMENDATION_URI + questionId, inputJson);
    }

    void nonRecommend(Long questionId) {
        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, NON_RECOMMENDATION);
        post(QUESTION_RECOMMENDATION_URI + questionId, inputJson);
    }

    RecommendationResponse recommendationCount(Long questionId) {
        return get(QUESTION_RECOMMENDATION_URI + questionId, RecommendationResponse.class);
    }

    void toggleToRecommended(Long questionId) {
        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, RECOMMENDATION);
        patch(QUESTION_RECOMMENDATION_URI + questionId, inputJson);
    }

    void toggleToNonRecommended(Long questionId) {
        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, NON_RECOMMENDATION);
        patch(QUESTION_RECOMMENDATION_URI + questionId, inputJson);
    }

    void deleteRecommendation(Long questionId) {
        delete(QUESTION_RECOMMENDATION_URI + questionId);
    }
}
