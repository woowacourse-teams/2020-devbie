package underdogs.devbie.recommendation.service;

import static org.mockito.BDDMockito.*;
import static underdogs.devbie.question.domain.QuestionTest.*;

import java.util.LinkedHashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.service.QuestionService;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;

@ExtendWith(MockitoExtension.class)
class QuestionRecommendationServiceTest {

    private QuestionRecommendationService questionRecommendationService;

    @Mock
    QuestionRecommendationRepository questionRecommendationRepository;

    @Mock
    QuestionService questionService;

    @BeforeEach
    void setUp() {
        this.questionRecommendationService = new QuestionRecommendationService(questionRecommendationRepository, questionService);
    }

    @DisplayName("추천 생성")
    @Test
    void createRecommendation() {
        Question question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build();
        willDoNothing().given(questionService).updateRecommendationCount(anyLong(), any(RecommendationType.class), anyBoolean());

        questionRecommendationService.createOrUpdateRecommendation(1L, 1L, RecommendationType.RECOMMENDED);

        verify(questionService).updateRecommendationCount(eq(1L), eq(RecommendationType.RECOMMENDED), eq(false));
    }

    @DisplayName("추천 삭제")
    @Test
    void deleteRecommendation() {
        QuestionRecommendation recommendation = QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        given(questionRecommendationRepository.findByObjectAndUserId(anyLong(), anyLong())).willReturn(
            Optional.of(recommendation));
        Question question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build();
        willDoNothing().given(questionService).decreaseRecommendationCount(anyLong(), any(RecommendationType.class));

        questionRecommendationService.deleteRecommendation(1L, 1L);

        verify(questionRecommendationRepository).findByObjectAndUserId(anyLong(), anyLong());
    }
}
