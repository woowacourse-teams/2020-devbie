package underdogs.devbie.notice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NoticeResponses {

    private List<NoticeResponse> noticeResponses;

    public static NoticeResponses from(List<NoticeResponse> noticeResponses) {
        return new NoticeResponses(new ArrayList<>(noticeResponses));
    }
}
