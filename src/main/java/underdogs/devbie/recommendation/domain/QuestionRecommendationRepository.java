package underdogs.devbie.recommendation.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRecommendationRepository extends JpaRepository<QuestionRecommendation, Long> {

    Long countByQuestionIdAndAndRecommendationType(Long questionId, RecommendationType recommendationType);

    Optional<QuestionRecommendation> findByQuestionIdAndUserId(Long questionId, Long userId);
}
