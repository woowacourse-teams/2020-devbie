package underdogs.devbie.recommendation.domain;

import java.util.Optional;

public interface RecommendationRepository<T extends Recommendation> {

    Optional<T> findByObjectAndUserId(Long objectId, Long userId);

    T save(T t);

    void delete(T t);
}
