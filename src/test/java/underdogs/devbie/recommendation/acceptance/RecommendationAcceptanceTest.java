package underdogs.devbie.recommendation.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

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

    /*
    Feature: 면접 질문 답변 추천 관리

     Scenario: 면접 질문 답변의 추천을 관리한다.

         When 1번 질문을 추천한다.
         Then 1번 질문의 추천수가 +1 증가했다.

         When 2번 질문을 비추천한다.
         Then 2번 질문의 추천수가 -1 감소했다.

         When 1번 질문을 비추천한다.
         Then 1번 질문의 추천수가 -1 감소했다.

         When 2번 질문을 추천한다.
         Then 2번 질문의 추천수가 +1 증가했다.

         When 1번 질문의 추천수를 삭제한다.
         Then 1번 질문의 추천수가 0이 되었다.

         When 2번 질문의 추천수를 삭제한다.
         Then 2번 질문의 추천수가 0이 되었다.
     */

    @DisplayName("추천 인수 테스트")
    @TestFactory
    Stream<DynamicTest> manageRecommendation() {
        return Stream.of(
            dynamicTest("1번 질문 추천", () -> {
                recommend(1L);

                Long question1Count = recommendationCount(1L).getRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),
            dynamicTest("2번 질문 비추천", () -> {
                nonRecommend(2L);

                Long question2Count = recommendationCount(2L).getNonRecommendedCount();
                assertThat(question2Count).isEqualTo(1L);
            }),
            dynamicTest("1번 질문 비추천", () -> {
                toggleToNonRecommended(1L);

                Long question1Count = recommendationCount(1L).getNonRecommendedCount();
                assertThat(question1Count).isEqualTo(1L);
            }),
            dynamicTest("2번 질문 추천", () -> {
                toggleToRecommended(2L);

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
