package underdogs.devbie.answer.domain;

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
            .selectFrom(QAnswer.answer)
            .where(QAnswer.answer.questionId.eq(questionId))
            .orderBy(QAnswer.answer.recommendationCount.recommendedCount.desc())
            .fetch();
    }
}
