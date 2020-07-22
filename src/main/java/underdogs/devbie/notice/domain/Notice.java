package underdogs.devbie.notice.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import underdogs.devbie.config.BaseTimeEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
}
