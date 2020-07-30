package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRecommendationRepository extends
    JpaRepository<QuestionRecommendation, Long>,
    RecommendationRepository<QuestionRecommendation> {

    @Override
    @Query("select q from QuestionRecommendation q "
        + "where q.questionId = :questionId")
    List<QuestionRecommendation> findByObjectId(Long questionId);

    @Override
    @Query("select q from QuestionRecommendation q "
        + "where q.questionId = :questionId  "
        + "and q.userId = :userId")
    Optional<QuestionRecommendation> findByObjectAndUserId(Long questionId, Long userId);
}
