package underdogs.devbie.notice.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Duration {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Duration duration = (Duration)o;
        return Objects.equals(startDate, duration.startDate) &&
            Objects.equals(endDate, duration.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
