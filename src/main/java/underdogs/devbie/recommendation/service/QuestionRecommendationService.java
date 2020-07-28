package underdogs.devbie.recommendation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.AlreadyExistException;
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
    public void createRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        recommendationRepository.findByObjectAndUserId(objectId, userId)
            .ifPresent(x -> new AlreadyExistException());

        recommendationRepository.save(QuestionRecommendation.of(objectId, userId, recommendationType));
    }
}
