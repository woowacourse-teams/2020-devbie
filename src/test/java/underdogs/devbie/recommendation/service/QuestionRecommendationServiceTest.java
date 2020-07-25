package underdogs.devbie.recommendation.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        questionRecommendationService.createRecommendation(1L, "RECOMMENDED", 1L);

        verify(questionRecommendationRepository).save(any());
    }
}