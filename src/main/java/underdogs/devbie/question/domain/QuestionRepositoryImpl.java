package underdogs.devbie.question.domain;

import static underdogs.devbie.question.domain.QQuestion.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class QuestionRepositoryImpl extends QuerydslRepositorySupport implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Question.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Question> findAllBy(
        String title, String content, Pageable pageable
    ) {
        JPAQuery<Question> limit = jpaQueryFactory
            .selectFrom(question)
            .where(containKeyword(title, content))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());
        QueryResults<Question> queryResults = getQuerydsl().applyPagination(pageable, limit).fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    private BooleanExpression containKeyword(String title, String content) {
        if (title.isEmpty() && content.isEmpty()) {
            return null;
        }

        BooleanExpression containTitle = question.isNull();
        BooleanExpression containContent = question.isNull();

        if (!title.isEmpty()) {
            containTitle = question
                .title
                .title
                .toLowerCase()
                .contains(title.toLowerCase());
        }

        if (!content.isEmpty()) {
            containContent = question
                .content
                .content
                .toLowerCase()
                .contains(content.toLowerCase());
        }

        return containTitle.or(containContent);
    }
}
