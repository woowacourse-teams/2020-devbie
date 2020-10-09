package underdogs.devbie.question.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.impl.JPAQueryFactory;
import underdogs.devbie.question.domain.QQuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtag;

public class QuestionHashtagRepositoryImpl extends QuerydslRepositorySupport
    implements QuestionHashtagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionHashtagRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QuestionHashtag.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public void deleteAllByHashtagIds(List<Long> hashtagIds, Long questionId) {
        jpaQueryFactory
            .delete(QQuestionHashtag.questionHashtag)
            .where(QQuestionHashtag.questionHashtag.hashtag.id.in(hashtagIds),
                QQuestionHashtag.questionHashtag.question.id.eq(questionId))
            .execute();
    }

    @Override
    public List<Long> findQuestionIdsByHashtagId(Long hashtagId) {
        return jpaQueryFactory
            .select(QQuestionHashtag.questionHashtag.question.id)
            .from(QQuestionHashtag.questionHashtag)
            .where(QQuestionHashtag.questionHashtag.hashtag.id.eq(hashtagId))
            .fetchResults()
            .getResults();
    }
}
