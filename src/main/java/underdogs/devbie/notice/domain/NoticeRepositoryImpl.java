package underdogs.devbie.notice.domain;

import static underdogs.devbie.notice.domain.QNotice.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Notice> findAllBy(
        NoticeType noticeType, JobPosition jobPosition,
        Language language, Pageable pageable
    ) {
        QueryResults<Notice> queryResults = jpaQueryFactory
            .selectFrom(notice)
            .innerJoin(notice.noticeDescription.languages)
            .fetchJoin()
            .where(equalNoticeType(noticeType),
                equalJobPosition(jobPosition),
                containLanguage(language))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

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
}
