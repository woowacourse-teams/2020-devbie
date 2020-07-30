package underdogs.devbie.recommendation.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @DisplayName("추천 수 조회")
    @Test
    void count() {
        answerRecommendationService.count(1L);

        verify(answerRecommendations).findByObjectId(anyLong());
    }

    @DisplayName("추천 생성")
    @Test
    void createRecommendation() {
        answerRecommendationService.createOrUpdateRecommendation(1L, 1L, RecommendationType.RECOMMENDED);

        // verify(answerRecommendations).save(any());
    }

    @DisplayName("추천 삭제")
    @Test
    void deleteRecommendation() {
        AnswerRecommendation recommendation = AnswerRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        given(answerRecommendations.findByObjectAndUserId(anyLong(), anyLong())).willReturn(
            Optional.of(recommendation));

        answerRecommendationService.deleteRecommendation(1L, 1L);

        verify(answerRecommendations).findByObjectAndUserId(anyLong(), anyLong());
        // verify(answerRecommendations).delete(any());
    }
}
