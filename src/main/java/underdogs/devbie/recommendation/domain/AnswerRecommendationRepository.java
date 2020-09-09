package underdogs.devbie.recommendation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRecommendationRepository extends
    JpaRepository<AnswerRecommendation, Long>,
    AnswerRecommendationRepositoryCustom,
    RecommendationRepository<AnswerRecommendation> {
}
