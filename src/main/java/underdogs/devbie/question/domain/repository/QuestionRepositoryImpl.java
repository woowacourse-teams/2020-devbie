package underdogs.devbie.question.domain.repository;

import static underdogs.devbie.question.domain.QQuestion.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import underdogs.devbie.question.domain.Question;

public class QuestionRepositoryImpl extends QuerydslRepositorySupport implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Question.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Question> findAllBy(Pageable pageable) {
        JPAQuery<Question> result = jpaQueryFactory
            .selectFrom(question);

        QueryResults<Question> queryResults = getQuerydsl().applyPagination(pageable, result).fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }
}
