package underdogs.devbie.notice.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDescription;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@ToString
public class NoticeCreateRequest {

    private String startDate;

    private String endDate;

    @NotBlank
    private String name;

    @Min(1)
    private Integer salary;

    @NotEmpty
    private List<String> languages;

    private JobPosition jobPosition;

    @NotBlank
    private String description;

    private String image;

    public Notice toEntity() {
        LocalDateTime startLocalDate = LocalDateTime.parse(startDate);
        LocalDateTime endLocalDate = LocalDateTime.parse(endDate);

        return Notice.builder()
            .company(new Company(name, salary))
            .duration(new Duration(startLocalDate, endLocalDate))
            .jobPosition(jobPosition)
            .noticeDescription(new NoticeDescription(new HashSet<>(languages), description))
            .image(image)
            .build();
    }
}
