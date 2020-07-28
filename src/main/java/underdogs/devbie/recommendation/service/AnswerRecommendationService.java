package underdogs.devbie.recommendation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.AlreadyExistException;
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
    public void createRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        recommendationRepository.findByObjectAndUserId(objectId, userId)
            .ifPresent(x -> new AlreadyExistException());

        recommendationRepository.save(AnswerRecommendation.of(objectId, userId, recommendationType));
    }
}
