package underdogs.devbie.notice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NoticeResponses {
    private final List<NoticeResponse> noticeResponses;

    public static NoticeResponses from(List<NoticeResponse> noticeResponses) {
        return new NoticeResponses(new ArrayList<>(noticeResponses));
    }
}
