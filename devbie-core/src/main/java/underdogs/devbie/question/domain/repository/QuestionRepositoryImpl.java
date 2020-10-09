package underdogs.devbie.question.domain.repository;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import underdogs.devbie.question.domain.QQuestion;
import underdogs.devbie.question.domain.Question;

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
        JPAQuery<Question> result = jpaQueryFactory
            .selectFrom(QQuestion.question)
            .where(containKeyword(title, content));

        QueryResults<Question> queryResults = getQuerydsl().applyPagination(pageable, result).fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    private BooleanBuilder containKeyword(String title, String content) {
        return new BooleanBuilder()
            .or(containTitle(title))
            .or(containContent(content));
    }

    private BooleanExpression containTitle(String title) {
        if (Objects.isNull(title) || title.trim().isEmpty()) {
            return null;
        }
        return QQuestion.question
            .title
            .title
            .toLowerCase()
            .contains(title.toLowerCase());
    }

    private BooleanExpression containContent(String content) {
        if (Objects.isNull(content) || content.trim().isEmpty()) {
            return null;
        }
        return QQuestion.question
            .content
            .content
            .toLowerCase()
            .contains(content.toLowerCase());
    }
}
