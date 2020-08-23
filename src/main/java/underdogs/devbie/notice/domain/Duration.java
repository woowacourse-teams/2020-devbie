package underdogs.devbie.notice.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.notice.expception.InvalidDurationException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
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
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return;
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidDurationException();
        }
    }
}
