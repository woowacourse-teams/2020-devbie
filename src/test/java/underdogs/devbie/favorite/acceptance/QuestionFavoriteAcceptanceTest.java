package underdogs.devbie.favorite.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;

public class QuestionFavoriteAcceptanceTest extends AcceptanceTest {

    public static final String QUESTION_FAVORITE_URI = "/api/favorite-question?objectType=question";
    private Long question1Id;
    private Long question2Id;
    private Long question3Id;

    @DisplayName("질문 즐겨찾기 인수 테스트")
    @TestFactory
    Stream<DynamicTest> manageFavorite() {

        return Stream.of(
            dynamicTest("질문 생성", () -> {
                question1Id = createQuestion("질문1");
                question2Id = createQuestion("질문2");
                question3Id = createQuestion("질문3");
            }),
            dynamicTest("1번 질문 즐겨찾기 추가", () -> {
                addFavorite(question1Id);
            }),
            dynamicTest("1번 질문 즐겨찾기 확인", () -> {
                QuestionResponses questionResponses = searchFavorite(userId);
                assertThat(questionResponses.getQuestions().get(0))
                    .extracting(QuestionResponse::getId)
                    .isEqualTo(question1Id);
            }),
            dynamicTest("1번 질문 즐겨찾기 취소", () -> {
                deleteFavorite(question1Id);

                QuestionResponses questionResponses = searchFavorite(userId);
                assertThat(questionResponses.getQuestions().size())
                    .isEqualTo(0);
            }),
            dynamicTest("여러 질문 즐겨찾기 추가", () -> {
                addFavorite(question1Id);
                addFavorite(question2Id);
                addFavorite(question3Id);
            }),
            dynamicTest("유저가 추가한 즐겨찾기 갯수 확인", () -> {
                QuestionResponses questionResponses = searchFavorite(userId);
                assertThat(questionResponses.getQuestions().size())
                    .isEqualTo(3);
            })
        );
    }

    private void deleteFavorite(Long objectId) {
        delete(QUESTION_FAVORITE_URI + "&objectId=" + objectId);
    }

    private QuestionResponses searchFavorite(Long userId) {
        return get(QUESTION_FAVORITE_URI + "&userId=" + userId, QuestionResponses.class);
    }

    private void addFavorite(long questionId) {
        post(QUESTION_FAVORITE_URI + "&objectId=" + questionId);
    }

}
