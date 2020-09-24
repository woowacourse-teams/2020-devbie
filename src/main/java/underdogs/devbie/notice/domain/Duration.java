package underdogs.devbie.notice.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Duration {

    @Enumerated(value = EnumType.STRING)
    private RecruitmentType recruitmentType;

    private LocalDate startDate;

    private LocalDate endDate;

    public Duration(RecruitmentType recruitmentType, LocalDate startDate, LocalDate endDate) {
        validateParameters(recruitmentType, startDate, endDate);
        this.recruitmentType = recruitmentType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateParameters(RecruitmentType recruitmentType, LocalDate startDate,
        LocalDate endDate) {
        if (recruitmentType.isAnyTimeRecruitment()) {
            return;
        }

        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            throw new CreateFailException();
        }

        if (startDate.isAfter(endDate)) {
            throw new CreateFailException();
        }
    }

    public Boolean isFinished() {
        if (recruitmentType.isAnyTimeRecruitment()) {
            return Boolean.FALSE;
        }
        return endDate.isBefore(LocalDate.now());
    }
}
