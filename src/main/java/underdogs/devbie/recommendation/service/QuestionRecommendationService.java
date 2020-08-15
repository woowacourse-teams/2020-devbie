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

    private QuestionService questionService;

    public QuestionRecommendationService(QuestionRecommendationRepository questionRecommendationRepository, QuestionService questionService) {
        this.recommendationRepository = questionRecommendationRepository;
        this.questionService = questionService;
    }

    @Override
    public void createOrUpdateRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        Optional<QuestionRecommendation> optionalQuestionRecommendation =
            recommendationRepository.findByObjectAndUserId(objectId, userId);

        QuestionRecommendation questionRecommendation =
            optionalQuestionRecommendation.orElse(QuestionRecommendation.of(objectId, userId, recommendationType));

        if (!questionRecommendation.hasRecommendationTypeOf(recommendationType)) {
            questionRecommendation.toggleRecommended();
        }
        recommendationRepository.save(questionRecommendation);
    }

    @Transactional
    public void deleteRecommendation(Long objectId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(objectId, userId);

        Recommendation recommendation = optRecommendation.orElseThrow(NotExistException::new);

        recommendationRepository.delete(recommendation);
    }
}
