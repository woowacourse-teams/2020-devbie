package underdogs.devbie.recommendation.domain;

import java.util.Optional;

public interface QuestionRecommendationRepositoryCustom {

    Optional<QuestionRecommendation> findByObjectAndUserId(Long questionId, Long userId);
}
