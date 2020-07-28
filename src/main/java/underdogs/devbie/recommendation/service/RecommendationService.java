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

    // 내부에는 다음과 같은 코드가 들어간다
    // 구현체를 생성해서 save해야 하므로 abstract으로 하위 클래스에 맡긴다
    //    recommendationRepository.findByObjectAndUserId(objectId, userId)
    //             .ifPresent(x -> new AlreadyExistException());
    //
    //    recommendationRepository.save(Recommendation.of(objectId, userId, recommendationType));
    @Transactional
    public abstract void createRecommendation(Long objectId, Long userId, RecommendationType recommendationType);

    @Transactional
    public void toggleRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        Optional<Recommendation> optionalRecommendation = recommendationRepository.findByObjectAndUserId(objectId,
            userId);

        Recommendation recommendation = optionalRecommendation
            .filter(re -> re.hasRecommendationTypeOf(recommendationType.toggleType()))
            .orElseThrow(NotExistException::new);

        recommendation.toggleRecommended();
    }

    @Transactional
    public void deleteRecommendation(Long objectId, Long userId) {
        Optional<Recommendation> optionalRecommendation = recommendationRepository.findByObjectAndUserId(objectId,
            userId);

        Recommendation recommendation = optionalRecommendation
            .orElseThrow(NotExistException::new);

        recommendationRepository.delete(recommendation);
    }
}
