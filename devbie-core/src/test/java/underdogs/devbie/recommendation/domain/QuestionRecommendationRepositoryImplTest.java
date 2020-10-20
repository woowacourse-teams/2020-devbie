package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.querydsl.jpa.impl.JPAQueryFactory;

@DataJpaTest
class QuestionRecommendationRepositoryImplTest {

    @Qualifier("questionRecommendationRepository")
    @Autowired
    private RecommendationRepository repository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    private QuestionRecommendationRepositoryImpl repositoryImpl;

    private QuestionRecommendation recommendation;

    @BeforeEach
    void setUp() {
        repositoryImpl = new QuestionRecommendationRepositoryImpl(jpaQueryFactory);

        recommendation = QuestionRecommendation.of(1L, 1L, RecommendationType.RECOMMENDED);
        repository.save(recommendation);
    }

    @DisplayName("answerId와 userId로 엔티티 조회")
    @Test
    void findByObjectIdAndUserId() {
        QuestionRecommendation findRecommendation = repositoryImpl.findByObjectAndUserId(1L, 1L).orElse(null);

        assertAll(
            () -> assertThat(findRecommendation).isNotNull(),
            () -> assertThat(findRecommendation.getQuestionId()).isEqualTo(1L),
            () -> assertThat(findRecommendation.getUserId()).isEqualTo(1L)
        );

    }

}