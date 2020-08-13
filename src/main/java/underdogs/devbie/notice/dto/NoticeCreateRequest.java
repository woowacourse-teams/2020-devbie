package underdogs.devbie.notice.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

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
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDescription;
import underdogs.devbie.notice.domain.NoticeType;

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
    private String title;

    private NoticeType noticeType;

    @NotBlank
    private String name;

    @Min(1)
    private Integer salary;

    @NotEmpty
    private Set<Language> languages;

    private JobPosition jobPosition;

    @NotBlank
    private String description;

    private String image;

    public Notice toEntity() {
        LocalDateTime startLocalDate = LocalDateTime.parse(startDate,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endLocalDate = LocalDateTime.parse(endDate,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return Notice.builder()
            .title(title)
            .noticeType(noticeType)
            .company(new Company(name, salary))
            .duration(new Duration(startLocalDate, endLocalDate))
            .jobPosition(jobPosition)
            .noticeDescription(new NoticeDescription(languages, description))
            .image(image)
            .build();
    }
}
