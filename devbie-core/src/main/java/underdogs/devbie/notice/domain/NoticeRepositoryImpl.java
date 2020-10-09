package underdogs.devbie.notice.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class NoticeRepositoryImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public NoticeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Notice.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Notice> findAllBy(
        NoticeType noticeType, JobPosition jobPosition,
        Language language, String keyword, Pageable pageable
    ) {
        JPAQuery<Notice> query = jpaQueryFactory
            .selectFrom(QNotice.notice)
            .leftJoin(QNotice.notice.noticeDescription.languages)
            .fetchJoin()
            .where(equalNoticeType(noticeType),
                equalJobPosition(jobPosition),
                containLanguage(language),
                containKeyword(keyword)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        List<Notice> notices = getQuerydsl().applyPagination(pageable, query)
            .fetch();

        return new PageImpl<>(notices, pageable, query.fetchCount());
    }

    private BooleanExpression equalNoticeType(NoticeType noticeType) {
        if (StringUtils.isEmpty(noticeType)) {
            return null;
        }
        return QNotice.notice
            .noticeType
            .eq(noticeType);
    }

    private BooleanExpression equalJobPosition(JobPosition jobPosition) {
        if (StringUtils.isEmpty(jobPosition)) {
            return null;
        }
        return QNotice.notice
            .jobPosition
            .eq(jobPosition);
    }

    private BooleanExpression containLanguage(Language language) {
        if (StringUtils.isEmpty(language)) {
            return null;
        }
        return QNotice.notice
            .noticeDescription
            .languages
            .contains(language);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword) || keyword.isEmpty()) {
            return null;
        }
        BooleanExpression containCompany = QNotice.notice
            .company
            .name
            .contains(keyword);
        BooleanExpression containTitle = QNotice.notice
            .title
            .contains(keyword);

        return containTitle.or(containCompany);
    }
}
