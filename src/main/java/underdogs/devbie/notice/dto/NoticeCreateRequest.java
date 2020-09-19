package underdogs.devbie.notice.dto;

import java.time.LocalDateTime;
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

    @NotEmpty
    private Set<Language> languages;

    private JobPosition jobPosition;

    @NotBlank
    private String description;

    private String image;

    public Notice toEntity() {
        Duration duration = createDuration();
        return Notice.builder()
            .title(title)
            .noticeType(noticeType)
            .company(new Company(name))
            .duration(duration)
            .jobPosition(jobPosition)
            .noticeDescription(new NoticeDescription(languages, description))
            .image(image)
            .build();
    }

    private Duration createDuration() {
        LocalDateTime startLocalDate = null;
        LocalDateTime endLocalDate = null;

        if (!startDate.isEmpty()) {
            startLocalDate = LocalDateTime.parse(startDate);
        }
        if (!endDate.isEmpty()) {
            endLocalDate = LocalDateTime.parse(endDate);
        }

        return new Duration(startLocalDate, endLocalDate);
    }
}
