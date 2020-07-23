package underdogs.devbie.notice.domain;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@EqualsAndHashCode
public class Duration {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Duration(LocalDateTime startDate, LocalDateTime endDate) {
        validateParameters(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateParameters(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
    }
}
