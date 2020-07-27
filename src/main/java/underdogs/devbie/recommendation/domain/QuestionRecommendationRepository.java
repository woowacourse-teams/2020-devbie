package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRecommendationRepository extends JpaRepository<QuestionRecommendation, Long> {

    Long countByQuestionIdAndRecommendationType(Long questionId, RecommendationType recommendationType);

    List<QuestionRecommendation> findByQuestionId(Long questionId);

    Optional<QuestionRecommendation> findByQuestionIdAndUserId(Long questionId, Long userId);
}
