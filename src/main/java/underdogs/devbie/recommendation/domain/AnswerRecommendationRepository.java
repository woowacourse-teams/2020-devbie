package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRecommendationRepository extends JpaRepository<AnswerRecommendation, Long> {

    Long countByAnswerIdAndRecommendationType(Long answerId, RecommendationType recommendationType);

    List<AnswerRecommendation> findByAnswerId(Long answerId);

    Optional<AnswerRecommendation> findByAnswerIdAndUserId(Long answerId, Long userId);
}
