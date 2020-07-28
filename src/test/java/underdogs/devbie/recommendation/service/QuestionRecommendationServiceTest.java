package underdogs.devbie.recommendation.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("추천 수 조회")
@Test
void count() {
    questionRecommendationService.count(1L);

    verify(questionRecommendationRepository).findByObjectId(anyLong());
}

    @DisplayName("추천 생성")
    @Test
    void createRecommendation() {
        questionRecommendationService.createOrUpdateRecommendation(1L, 1L, RecommendationType.RECOMMENDED);

        // verify(questionRecommendationRepository).save(any());
    }

    @DisplayName("추천 삭제")
    @Test
    void deleteRecommendation() {
        QuestionRecommendation recommendation = QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        given(questionRecommendationRepository.findByObjectAndUserId(anyLong(), anyLong())).willReturn(
            Optional.of(recommendation));

        questionRecommendationService.deleteRecommendation(1L, 1L);

        verify(questionRecommendationRepository).findByObjectAndUserId(anyLong(), anyLong());
        // verify(answerRecommendations).delete(any());
    }
}
