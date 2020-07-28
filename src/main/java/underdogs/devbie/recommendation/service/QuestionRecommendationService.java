package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
@Transactional(readOnly = true)
public class QuestionRecommendationService extends RecommendationService {

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
