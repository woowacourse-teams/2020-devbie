package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRecommendationRepository extends
    JpaRepository<AnswerRecommendation, Long>,
    RecommendationRepository<AnswerRecommendation> {

    @Override
    @Query("select a from AnswerRecommendation a "
        + "where a.answerId = :answerId")
    List<AnswerRecommendation> findByObjectId(@Param("answerId") Long answerId);

    @Override
    @Query("select a from AnswerRecommendation a "
        + "where a.answerId = :answerId  "
        + "and a.userId = :userId")
    Optional<AnswerRecommendation> findByObjectAndUserId(@Param("answerId") Long answerId, @Param("userId")Long userId);
}
