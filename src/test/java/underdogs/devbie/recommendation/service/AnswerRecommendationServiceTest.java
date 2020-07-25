package underdogs.devbie.recommendation.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void count() {
        answerRecommendationService.count(1L);

        verify(answerRecommendations).countByAnswerIdAndAndRecommendationType(anyLong(), eq(RecommendationType.RECOMMENDED));
        verify(answerRecommendations).countByAnswerIdAndAndRecommendationType(anyLong(), eq(RecommendationType.NON_RECOMMENDED));
    }

    @Test
    void createRecommendation() {
        answerRecommendationService.createRecommendation(1L, 1L, RecommendationType.RECOMMENDED);

        verify(answerRecommendations).save(any());
    }

    @Test
    void toggleRecommendation() {
        AnswerRecommendation recommendation = AnswerRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        given(answerRecommendations.findByAnswerIdAndUserId(anyLong(), anyLong())).willReturn(Optional.of(recommendation));

        answerRecommendationService.toggleRecommendation(1L, 1L, RecommendationType.NON_RECOMMENDED);

        verify(answerRecommendations).findByAnswerIdAndUserId(anyLong(), anyLong());
    }

    @Test
    void deleteRecommendation() {
        AnswerRecommendation recommendation = AnswerRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        given(answerRecommendations.findByAnswerIdAndUserId(anyLong(), anyLong())).willReturn(Optional.of(recommendation));

        answerRecommendationService.deleteRecommendation(1L, 1L);

        verify(answerRecommendations).findByAnswerIdAndUserId(anyLong(), anyLong());
        verify(answerRecommendations).delete(any());
    }
}