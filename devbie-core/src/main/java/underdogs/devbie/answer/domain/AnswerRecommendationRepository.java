package underdogs.devbie.answer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import underdogs.devbie.recommendation.domain.RecommendationRepository;

public interface AnswerRecommendationRepository extends
    JpaRepository<AnswerRecommendation, Long>,
    RecommendationRepository<AnswerRecommendation>,
    AnswerRecommendationRepositoryCustom {
}
