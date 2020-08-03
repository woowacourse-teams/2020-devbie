package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRecommendationRepository extends
    JpaRepository<QuestionRecommendation, Long>,
    RecommendationRepository<QuestionRecommendation> {

    @Override
    @Query("select q from QuestionRecommendation q "
        + "where q.questionId = :questionId")
    List<QuestionRecommendation> findByObjectId(@Param("questionId") Long questionId);

    @Override
    @Query("select q from QuestionRecommendation q "
        + "where q.questionId = :questionId  "
        + "and q.userId = :userId")
    Optional<QuestionRecommendation> findByObjectAndUserId(@Param("questionId") Long questionId,
        @Param("userId") Long userId);
}
