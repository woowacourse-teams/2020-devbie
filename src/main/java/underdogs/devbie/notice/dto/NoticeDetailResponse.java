package underdogs.devbie.notice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDetail;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class NoticeDetailResponse {
    private Long id;
    private Company company;
    private Duration duration;
    private JobPosition jobPosition;
    private NoticeDetail noticeDetail;
    private String image;

    public static NoticeDetailResponse from(Notice notice) {
        return NoticeDetailResponse.builder()
            .id(notice.getId())
            .company(notice.getCompany())
            .noticeDetail(notice.getNoticeDetail())
            .jobPosition(notice.getJobPosition())
            .image(notice.getImage())
            .duration(notice.getDuration())
            .build();
    }
}
