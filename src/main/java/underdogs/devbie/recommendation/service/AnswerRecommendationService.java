package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.recommendation.domain.AnswerRecommendation;
import underdogs.devbie.recommendation.domain.AnswerRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
@Transactional(readOnly = true)
public class AnswerRecommendationService extends RecommendationService {

    public AnswerRecommendationService(AnswerRecommendationRepository answerRecommendationRepository) {
        this.recommendationRepository = answerRecommendationRepository;
    }

    @Override
    public void createOrUpdateRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        Optional<AnswerRecommendation> optionalAnswerRecommendation =
            recommendationRepository.findByObjectAndUserId(objectId, userId);

        AnswerRecommendation answerRecommendation =
            optionalAnswerRecommendation.orElse(AnswerRecommendation.of(objectId, userId, recommendationType));

        if (!answerRecommendation.hasRecommendationTypeOf(recommendationType)) {
            answerRecommendation.toggleRecommended();
        }

        recommendationRepository.save(answerRecommendation);
    }
}
