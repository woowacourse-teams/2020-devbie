package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
public class QuestionRecommendationService extends RecommendationService<QuestionRecommendation> {

    public QuestionRecommendationService(QuestionRecommendationRepository questionRecommendationRepository) {
        this.recommendationRepository = questionRecommendationRepository;
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
}
