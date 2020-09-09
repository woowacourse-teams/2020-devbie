package underdogs.devbie.recommendation.domain;

import java.util.Optional;

public interface AnswerRecommendationRepositoryCustom {

    Optional<AnswerRecommendation> findByObjectAndUserId(Long answerId, Long userId);
}
