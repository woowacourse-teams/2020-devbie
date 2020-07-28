package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository<T extends Recommendation> {

    List<? extends Recommendation> findByObjectId(Long objectId);

    Optional<? extends Recommendation> findByObjectAndUserId(Long objectId, Long userId);

    T save(T t);

    void delete(T t);
}
