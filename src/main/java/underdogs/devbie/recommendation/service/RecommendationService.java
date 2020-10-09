package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Transactional(readOnly = true)
public abstract class RecommendationService<T extends Recommendation> {

    protected RecommendationRepository recommendationRepository;

    public RecommendationResponse search(Long objectId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(objectId, userId);

        return RecommendationResponse.from(optRecommendation);
    }

    @Transactional
    public abstract void deleteRecommendation(Long objectId, Long userId);

    @Transactional
    public abstract void createOrUpdateRecommendation(Long objectId, Long userId,
        RecommendationType recommendationType);
}
