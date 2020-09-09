package underdogs.devbie.recommendation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRecommendationRepository extends
    JpaRepository<QuestionRecommendation, Long>,
    QuestionRecommendationRepositoryCustom,
    RecommendationRepository<QuestionRecommendation> {
}
