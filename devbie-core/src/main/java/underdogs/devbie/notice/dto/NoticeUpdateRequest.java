package underdogs.devbie.notice.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

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
import underdogs.devbie.notice.domain.RecruitmentType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@ToString
public class NoticeUpdateRequest {

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

    @NotEmpty
    @URL
    private String applyUrl;

    @NotNull
    private RecruitmentType recruitmentType;

    public Notice toEntity(Long id) {
        Duration duration = createDuration();

        return Notice.builder()
            .id(id)
            .title(title)
            .noticeType(noticeType)
            .company(new Company(name))
            .duration(duration)
            .jobPosition(jobPosition)
            .noticeDescription(new NoticeDescription(languages, description, applyUrl))
            .image(image)
            .build();
    }

    private Duration createDuration() {
        LocalDate startLocalDate = null;
        LocalDate endLocalDate = null;

        if (Objects.nonNull(startDate) && !startDate.isEmpty()) {
            startLocalDate = LocalDate.parse(startDate);
        }
        if (Objects.nonNull(endDate) && !endDate.isEmpty()) {
            endLocalDate = LocalDate.parse(endDate);
        }

        return new Duration(recruitmentType, startLocalDate, endLocalDate);
    }
}
