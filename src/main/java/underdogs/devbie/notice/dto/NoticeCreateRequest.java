package underdogs.devbie.notice.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDetail;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class NoticeCreateRequest {
    private String startDate;
    private String endDate;

    private String name;
    private Integer salary;
    private List<String> languages;

    private String jobPosition;
    private String description;
    private String image;

    public Notice toEntity() {
        LocalDateTime startLocalDate = LocalDateTime.parse(startDate);
        LocalDateTime endLocalDate = LocalDateTime.parse(endDate);

        return Notice.builder()
            .company(new Company(name, salary))
            .duration(new Duration(startLocalDate, endLocalDate))
            .jobPosition(JobPosition.valueOf(jobPosition))
            .noticeDetail(new NoticeDetail(new HashSet<>(languages), description))
            .image(image)
            .build();
    }
}
