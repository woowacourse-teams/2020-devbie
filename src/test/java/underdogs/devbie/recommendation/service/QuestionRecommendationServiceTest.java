package underdogs.devbie.recommendation.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;

@ExtendWith(MockitoExtension.class)
class QuestionRecommendationServiceTest {

    private QuestionRecommendationService questionRecommendationService;

    @Mock
    QuestionRecommendationRepository questionRecommendationRepository;

    @BeforeEach
    void setUp() {
        this.questionRecommendationService = new QuestionRecommendationService(questionRecommendationRepository);
    }

    @Test
    void count() {
        questionRecommendationService.count(1L);

        verify(questionRecommendationRepository).countByQuestionIdAndAndRecommendationType(anyLong(), eq(RecommendationType.RECOMMENDED));
        verify(questionRecommendationRepository).countByQuestionIdAndAndRecommendationType(anyLong(), eq(RecommendationType.NON_RECOMMENDED));
    }

    @Test
    void createRecommendation() {
        questionRecommendationService.createRecommendation(1L, 1L, RecommendationType.RECOMMENDED);

        verify(questionRecommendationRepository).save(any());
    }

    @Test
    void toggleRecommendation() {
        QuestionRecommendation recommendation = QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        given(questionRecommendationRepository.findByQuestionIdAndUserId(anyLong(), anyLong())).willReturn(
            Optional.of(recommendation));

        questionRecommendationService.toggleRecommendation(1L, 1L, RecommendationType.NON_RECOMMENDED);

        verify(questionRecommendationRepository).findByQuestionIdAndUserId(anyLong(), anyLong());
    }
}