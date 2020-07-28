package underdogs.devbie.recommendation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.recommendation.dto.RecommendationCountResponse;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@ExtendWith(MockitoExtension.class)
public class RecommendationAcceptanceTest extends AcceptanceTest {

    public static final String RECOMMENDATION_TYPE_FORMAT = "{\"recommendationType\":\"%s\"}";
    public static final String RECOMMENDATION = "RECOMMENDED";
    public static final String NON_RECOMMENDATION = "NON_RECOMMENDED";
    public static final String QUESTION_RECOMMENDATION_URI = "/api/recommendation-question?objectId=";

    @DisplayName("추천 인수 테스트")
    @Test
    void manageRecommendation() {
        // 1번 질문을 추천 한다
        recommend(1L);

        // 2번 질문을 비추천 한다
        nonRecommend(2L);

        // 1번 질문에 추천 수가 1이다
        Long question1Count = recommendationCount(1L).getRecommendedCount();
        assertThat(question1Count).isEqualTo(1L);

        // 1번 질문에 추천이 눌려있다
        String question1Type = searchRecommendation(1L, userId).getRecommendationType();
        assertThat(question1Type).isEqualTo("RECOMMENDED");

        // 2번 질문에 비추천 수가 1이다
        Long question2Count = recommendationCount(2L).getNonRecommendedCount();
        assertThat(question2Count).isEqualTo(1L);

        // 2번 질문에 비 추천이 눌려있다
        String question2Type = searchRecommendation(2L, userId).getRecommendationType();
        assertThat(question2Type).isEqualTo("NON_RECOMMENDED");

        // 3번 질문에 아무것도 눌려있지 않다
        String question3Type = searchRecommendation(3L, userId).getRecommendationType();
        assertThat(question3Type).isEqualTo("NOT_EXIST");

        // 1번 질문을 다시 추천한다
        recommend(1L);

        // 다시 추천해도 추천수가 증가하지 않는다
        question1Count = recommendationCount(1L).getRecommendedCount();
        assertThat(question1Count).isEqualTo(1L);

        // 1번 질문을 비추천으로 변경한다
        nonRecommend(1L);

        // 2번 질문을 추천으로 변경한다
        recommend(2L);

        // 1번 질문에 비추천 수가 1이다
        question1Count = recommendationCount(1L).getNonRecommendedCount();
        assertThat(question1Count).isEqualTo(1L);

        // 2번 질문에 추천 수가 1이다
        question2Count = recommendationCount(2L).getRecommendedCount();
        assertThat(question2Count).isEqualTo(1L);

        // 1번 질문의 추천 기록을 삭제한다
        deleteRecommendation(1L);

        // 2번 질문의 추천 기록을 삭제한다
        deleteRecommendation(2L);

        // 1번 질문에 비추천 수가 0이다
        question1Count = recommendationCount(1L).getNonRecommendedCount();
        assertThat(question1Count).isEqualTo(0L);

        // 2번 질문에 추천 수가 0이다
        question2Count = recommendationCount(2L).getRecommendedCount();
        assertThat(question2Count).isEqualTo(0L);
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
