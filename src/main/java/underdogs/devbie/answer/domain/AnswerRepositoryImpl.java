package underdogs.devbie.answer.domain;

import static underdogs.devbie.answer.domain.QAnswer.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AnswerRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Answer> findByQuestionIdOrderByRecommendationCount(Long questionId) {
        return jpaQueryFactory
            .selectFrom(answer)
            .where(answer.questionId.eq(questionId))
            .orderBy(answer.recommendationCount.recommendedCount.desc())
            .fetch();
    }
}
