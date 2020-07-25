package underdogs.devbie.recommendation.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRecommendationRepository extends JpaRepository<AnswerRecommendation, Long> {

    Long countByAnswerIdAndAndRecommendationType(Long questionId, RecommendationType recommendationType);

    Optional<AnswerRecommendation> findByAnswerIdAndUserId(Long questionId, Long userId);
}
