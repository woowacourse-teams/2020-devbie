package underdogs.devbie.recommendation.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static underdogs.devbie.question.acceptance.QuestionAcceptanceTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.question.dto.QuestionResponse;
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
            dynamicTest("1번 질문 생성 및 초기상태 확인", () -> {
                createQuestion(TEST_QUESTION_TITLE);

                String questionType = searchRecommendation(1L, userId).getRecommendationType();
                assertThat(questionType).isEqualTo("NOT_EXIST");
            }),
            dynamicTest("1번 질문 추천", () -> {
                recommend(1L);

                QuestionResponse questionResponse = fetchFirstQuestion();
                assertThat(questionResponse.getRecommendedCount()).isEqualTo(1L);
            }),
            dynamicTest("1번 질문 추천 갯수 확인", () -> {
                String question1Type = searchRecommendation(1L, userId).getRecommendationType();
                assertThat(question1Type).isEqualTo("RECOMMENDED");
            }),
            dynamicTest("1번 질문 비추천", () -> {
                nonRecommend(1L);

                QuestionResponse questionResponse = fetchFirstQuestion();
                assertThat(questionResponse.getNonRecommendedCount()).isEqualTo(1L);
            }),
            dynamicTest("1번 질문 추천 갯수 확인", () -> {
                String question1Type = searchRecommendation(1L, userId).getRecommendationType();
                assertThat(question1Type).isEqualTo("NON_RECOMMENDED");
            }),
            dynamicTest("1번 질문 추천 기록 삭제", () -> {
                deleteRecommendation(1L);

                QuestionResponse questionResponse = fetchFirstQuestion();
                assertThat(questionResponse.getNonRecommendedCount()).isEqualTo(0L);
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

    void deleteRecommendation(Long questionId) {
        delete(QUESTION_RECOMMENDATION_URI + questionId);
    }
}
