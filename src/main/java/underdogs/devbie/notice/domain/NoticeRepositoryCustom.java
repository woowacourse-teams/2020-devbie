package underdogs.devbie.notice.domain;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<Notice> findAllBy(NoticeType noticeType, JobPosition jobPosition, Language language);
}
