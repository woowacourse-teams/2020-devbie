package underdogs.devbie.question.domain.repository;

import static underdogs.devbie.question.domain.QQuestionHashtag.*;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.impl.JPAQueryFactory;
import underdogs.devbie.question.domain.QuestionHashtag;

public class QuestionHashtagRepositoryImpl extends QuerydslRepositorySupport
    implements QuestionHashtagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionHashtagRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QuestionHashtag.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public void deleteAllByHashtagIds(List<Long> hashtagIds) {
        jpaQueryFactory
            .delete(questionHashtag)
            .where(questionHashtag.hashtag.id.in(hashtagIds))
            .execute();
    }

    @Override
    public List<Long> findQuestionIdsByHashtagId(Long hashtagId) {
        return jpaQueryFactory
            .select(questionHashtag.question.id)
            .from(questionHashtag)
            .where(questionHashtag.hashtag.id.eq(hashtagId))
            .fetchResults()
            .getResults();
    }
}
