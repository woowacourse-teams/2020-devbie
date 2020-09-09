package underdogs.devbie.answer.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.questionId = :questionId "
        + "order by a.recommendationCount.recommendedCount desc")
    List<Answer> findByQuestionIdOrderByRecommendationCount(@Param("questionId") Long questionId);
}
