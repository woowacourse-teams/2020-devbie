package underdogs.devbie.notice.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

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
public class NoticeUpdateRequest {

    private String startDate;
    private String endDate;

    private String name;
    private Integer salary;
    private List<String> languages;

    private JobPosition jobPosition;
    private String description;
    private String image;

    public Notice toEntity(Long id) {
        LocalDateTime startLocalDate = LocalDateTime.parse(startDate);
        LocalDateTime endLocalDate = LocalDateTime.parse(endDate);

        return Notice.builder()
            .id(id)
            .company(new Company(name, salary))
            .duration(new Duration(startLocalDate, endLocalDate))
            .jobPosition(jobPosition)
            .noticeDetail(new NoticeDetail(new HashSet<>(languages), description))
            .image(image)
            .build();
    }
}
