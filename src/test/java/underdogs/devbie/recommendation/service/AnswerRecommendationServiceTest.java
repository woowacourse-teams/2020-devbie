package underdogs.devbie.recommendation.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.answer.domain.AnswerTest.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.recommendation.domain.AnswerRecommendation;
import underdogs.devbie.recommendation.domain.AnswerRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;

@ExtendWith(MockitoExtension.class)
class AnswerRecommendationServiceTest {

    private AnswerRecommendationService answerRecommendationService;

    @Mock
    AnswerRecommendationRepository answerRecommendations;

    @BeforeEach
    void setUp() {
        this.answerRecommendationService = new AnswerRecommendationService(answerRecommendations);
    }

    @DisplayName("추천 생성")
    @Test
    void createRecommendation() {
        Answer answer = Answer.builder()
            .id(1L)
            .userId(1L)
            .questionId(1L)
            .content(TEST_ANSWER_CONTENT)
            .build();

        answerRecommendationService.createOrUpdateRecommendation(1L, 1L, RecommendationType.RECOMMENDED);
    }

    @DisplayName("추천 삭제")
    @Test
    void deleteRecommendation() {
        AnswerRecommendation recommendation = AnswerRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        Answer answer = Answer.builder()
            .id(1L)
            .userId(1L)
            .questionId(1L)
            .content(TEST_ANSWER_CONTENT)
            .build();
        given(answerRecommendations.findByObjectAndUserId(anyLong(), anyLong())).willReturn(
            Optional.of(recommendation));

        answerRecommendationService.deleteRecommendation(1L, 1L);

        verify(answerRecommendations).findByObjectAndUserId(anyLong(), anyLong());
    }
}
