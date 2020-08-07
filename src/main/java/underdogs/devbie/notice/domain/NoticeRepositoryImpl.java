package underdogs.devbie.notice.domain;

import static underdogs.devbie.notice.domain.QNotice.*;

import java.util.List;

import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notice> findAllBy(NoticeType noticeType, JobPosition jobPosition, Language language) {
        return jpaQueryFactory
            .selectFrom(notice)
            .where(equalNoticeType(noticeType),
                equalJobPosition(jobPosition),
                containLanguage(language))
            .fetch();
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
