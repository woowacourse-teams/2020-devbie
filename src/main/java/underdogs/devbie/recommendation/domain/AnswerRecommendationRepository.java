package underdogs.devbie.recommendation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRecommendationRepository extends
    JpaRepository<AnswerRecommendation, Long>,
    RecommendationRepository<AnswerRecommendation> {

    @Override
    @Query("select a from AnswerRecommendation a "
        + "where a.answerId = :answerId")
    List<AnswerRecommendation> findByObjectId(Long answerId);

    @Override
    @Query("select a from AnswerRecommendation a " 
        + "where a.answerId = :answerId  "
        + "and a.userId = :userId")
    Optional<AnswerRecommendation> findByObjectAndUserId(Long answerId, Long userId);
}
