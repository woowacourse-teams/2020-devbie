package underdogs.devbie.recommendation.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
class QuestionRecommendationRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Qualifier("questionRecommendationRepository")
    @Autowired
    private RecommendationRepository repository;

    private JPAQueryFactory jpaQueryFactory;
    private QuestionRecommendationRepositoryImpl repositoryImpl;

    private QuestionRecommendation recommendation;

    @BeforeEach
    void setUp() {
        jpaQueryFactory = new JPAQueryFactory(em);
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