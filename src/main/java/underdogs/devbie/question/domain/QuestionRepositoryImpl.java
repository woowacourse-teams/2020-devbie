package underdogs.devbie.question.domain;

import static underdogs.devbie.notice.domain.QNotice.*;
import static underdogs.devbie.question.domain.QQuestion.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeType;

public class QuestionRepositoryImpl extends QuerydslRepositorySupport implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Question.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Question> findAllBy(
         Pageable pageable
    ) {
        JPAQuery<Question> limit = jpaQueryFactory
            .selectFrom(question)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());
        QueryResults<Question> queryResults = getQuerydsl().applyPagination(pageable, limit).fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    private BooleanExpression equalNoticeType(NoticeType noticeType) {
        if (StringUtils.isEmpty(noticeType)) {
            return null;
        }
        return notice
            .noticeType
            .eq(noticeType);
    }

    private BooleanExpression equalJobPosition(JobPosition jobPosition) {
        if (StringUtils.isEmpty(jobPosition)) {
            return null;
        }
        return notice
            .jobPosition
            .eq(jobPosition);
    }

    private BooleanExpression containLanguage(Language language) {
        if (StringUtils.isEmpty(language)) {
            return null;
        }
        return notice
            .noticeDescription
            .languages
            .contains(language);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword) || keyword.isEmpty()) {
            return null;
        }
        BooleanExpression containCompany = notice
            .company
            .name
            .contains(keyword);
        BooleanExpression containTitle = notice
            .title
            .contains(keyword);

        return containTitle.or(containCompany);
    }
}
