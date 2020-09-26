package underdogs.devbie.question.domain.repository;

import static underdogs.devbie.question.domain.QHashtag.*;
import static underdogs.devbie.question.domain.QQuestionHashtag.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import underdogs.devbie.question.domain.Hashtag;

public class HashtagRepositoryImpl implements HashtagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public HashtagRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public void deleteEmptyRefHashtag(List<Hashtag> hashtags) {
        hashtags.forEach(h ->
            jpaQueryFactory
            .delete(hashtag)
            .where(neverReferenced(h), hashtag.id.eq(h.getId()))
            .execute()
        );
    }

    private BooleanExpression neverReferenced(Hashtag hashtag) {
        long refCount = jpaQueryFactory
            .selectFrom(questionHashtag)
            .where(questionHashtag.hashtag.id.eq(hashtag.getId()))
            .fetchCount();
        return Expressions.asBoolean(refCount == 0).isTrue();
    }
}
