package underdogs.devbie.recommendation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

public abstract class RecommendationService<T extends Recommendation> {

    protected RecommendationRepository recommendationRepository;

    public RecommendationResponse count(Long objectId) {
        List<QuestionRecommendation> recommendations = recommendationRepository.findByObjectId(objectId);

        return RecommendationResponse.fromQuestionRecommendation(recommendations);
    }

    @Transactional
    public void deleteRecommendation(Long objectId, Long userId) {
        Optional<Recommendation> optionalRecommendation =
            recommendationRepository.findByObjectAndUserId(objectId, userId);

        Recommendation recommendation = optionalRecommendation.orElseThrow(NotExistException::new);

        recommendationRepository.delete(recommendation);
    }

    // save시 구체적인 클래스를 명시해주기 위해 abstract
    @Transactional
    public abstract void createOrUpdateRecommendation(Long objectId, Long id, RecommendationType recommendationType);
}
