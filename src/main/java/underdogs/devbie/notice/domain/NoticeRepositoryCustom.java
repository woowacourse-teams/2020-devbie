package underdogs.devbie.notice.domain;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<Notice> findBy(NoticeType noticeType, JobPosition jobPosition, Language language);
}
