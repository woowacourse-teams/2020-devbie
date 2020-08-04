package underdogs.devbie.notice.domain;

import java.util.Objects;

import javax.persistence.Column;
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
import lombok.ToString;
import underdogs.devbie.config.BaseTimeEntity;
import underdogs.devbie.notice.expception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_type", nullable = false)
    private NoticeType noticeType;

    @Embedded
    @Column(nullable = false)
    private Company company;

    @Embedded
    @Column(nullable = true)
    private Duration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_position", nullable = false)
    private JobPosition jobPosition;

    @Embedded
    @Column(nullable = false)
    private NoticeDescription noticeDescription;

    @Column(nullable = true)
    private String image;

    @Builder
    private Notice(Long id, String title, NoticeType noticeType, Company company, Duration duration,
        JobPosition jobPosition,
        NoticeDescription noticeDescription, String image) {
        validateParameters(title, noticeType, company, jobPosition, noticeDescription);
        this.id = id;
        this.title = title;
        this.noticeType = noticeType;
        this.company = company;
        this.duration = duration;
        this.jobPosition = jobPosition;
        this.noticeDescription = noticeDescription;
        this.image = image;
    }

    private void validateParameters(String title, NoticeType noticeType, Company company, JobPosition jobPosition,
        NoticeDescription noticeDescription) {
        if (Objects.isNull(title)
            || Objects.isNull(noticeType)
            || Objects.isNull(company)
            || Objects.isNull(jobPosition)
            || Objects.isNull(noticeDescription)) {
            throw new CreateFailException();
        }
    }

    public void update(Notice notice) {
        this.title = notice.title;
        this.noticeType = notice.noticeType;
        this.company = notice.company;
        this.duration = notice.duration;
        this.jobPosition = notice.jobPosition;
        this.noticeDescription = notice.noticeDescription;
        this.image = notice.image;
    }
}
