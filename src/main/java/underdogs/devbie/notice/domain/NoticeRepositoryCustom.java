package underdogs.devbie.notice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

    Page<Notice> findAllBy(NoticeType noticeType, JobPosition jobPosition,
        Language language, Pageable pageable);
}
