package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.domain.Recommendations;
import underdogs.devbie.recommendation.dto.RecommendationCountResponse;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Transactional(readOnly = true)
public abstract class RecommendationService<T extends Recommendation> {

    protected RecommendationRepository recommendationRepository;

    public RecommendationResponse search(Long objectId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(objectId, userId);

        return RecommendationResponse.from(optRecommendation);
    }

    public RecommendationCountResponse count(Long objectId) {
        Recommendations recommendations = Recommendations.from(recommendationRepository.findByObjectId(objectId));

        return RecommendationCountResponse.from(recommendations);
    }

    @Transactional
    public void deleteRecommendation(Long objectId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(objectId, userId);

        Recommendation recommendation = optRecommendation.orElseThrow(NotExistException::new);

        recommendationRepository.delete(recommendation);
    }

    // save시 구체적인 클래스를 명시해주기 위해 abstract
    @Transactional
    public abstract void createOrUpdateRecommendation(Long objectId, Long id, RecommendationType recommendationType);
}
