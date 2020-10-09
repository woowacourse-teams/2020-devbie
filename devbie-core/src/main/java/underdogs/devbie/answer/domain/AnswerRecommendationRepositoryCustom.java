package underdogs.devbie.answer.domain;

import java.util.Optional;

public interface AnswerRecommendationRepositoryCustom {

    Optional<AnswerRecommendation> findByObjectAndUserId(Long answerId, Long userId);
}
