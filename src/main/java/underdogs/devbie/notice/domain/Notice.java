package underdogs.devbie.notice.domain;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import underdogs.devbie.config.BaseTimeEntity;
import underdogs.devbie.notice.expception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Company company;
    @Embedded
    private Duration duration;
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;
    @Embedded
    private NoticeDetail noticeDetail;
    private String image;

    @Builder
    private Notice(Long id, Company company, Duration duration, JobPosition jobPosition,
        NoticeDetail noticeDetail, String image) {
        validateParameters(company, duration, jobPosition, noticeDetail, image);
        this.id = id;
        this.company = company;
        this.duration = duration;
        this.jobPosition = jobPosition;
        this.noticeDetail = noticeDetail;
        this.image = image;
    }

    private void validateParameters(Company company, Duration duration, JobPosition jobPosition,
        NoticeDetail noticeDetail, String image) {
        if (Objects.isNull(company)
            || Objects.isNull(duration)
            || Objects.isNull(jobPosition)
            || Objects.isNull(noticeDetail)
            || Objects.isNull(image) || image.isEmpty()) {
            throw new CreateFailException();
        }
    }

    public void update(Notice notice) {
        if (!this.company.equals(notice.company)) {
            this.company = notice.company;
        }
        if (!this.duration.equals(notice.duration)) {
            this.duration = notice.duration;
        }
        if (!this.jobPosition.equals(notice.jobPosition)) {
            this.jobPosition = notice.jobPosition;
        }
        if (!this.noticeDetail.equals(notice.noticeDetail)) {
            this.noticeDetail = notice.noticeDetail;
        }
        if (!this.image.equals(notice.image)) {
            this.image = notice.image;
        }
    }

    public String getCompanyName() {
        return company.getName();
    }

    public Set<String> getLanguages() {
        return noticeDetail.getLanguages();
    }
}
