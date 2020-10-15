package underdogs.devbie.notice.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import underdogs.devbie.notice.dto.NoticeReadRequest;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NoticeCacheVo {

    private final NoticeReadRequest noticeReadRequest;
    private final int page;
}
