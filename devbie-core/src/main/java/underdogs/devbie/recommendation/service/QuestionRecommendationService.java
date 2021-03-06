package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.question.service.QuestionService;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
public class QuestionRecommendationService extends RecommendationService<QuestionRecommendation> {

    private final QuestionService questionService;

    public QuestionRecommendationService(
        QuestionRecommendationRepository questionRecommendationRepository,
        QuestionService questionService
    ) {
        this.recommendationRepository = questionRecommendationRepository;
        this.questionService = questionService;
    }

    @Override
    public void createOrUpdateRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        Optional<QuestionRecommendation> optionalQuestionRecommendation =
            recommendationRepository.findByObjectAndUserId(questionId, userId);

        QuestionRecommendation questionRecommendation =
            optionalQuestionRecommendation.orElse(QuestionRecommendation.of(questionId, userId, recommendationType));

        boolean toggle = false;
        if (!questionRecommendation.hasRecommendationTypeOf(recommendationType)) {
            questionRecommendation.toggleRecommended();
            toggle = true;
        }
        recommendationRepository.save(questionRecommendation);
        questionService.toggleCount(questionId, recommendationType, toggle);
    }

    @Transactional
    public void deleteRecommendation(Long questionId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(questionId, userId);

        Recommendation recommendation = optRecommendation.orElseThrow(() -> new NotExistException("Recommendation"));

        recommendationRepository.delete(recommendation);
        questionService.decreaseCount(questionId, recommendation.getRecommendationType());
    }
}
