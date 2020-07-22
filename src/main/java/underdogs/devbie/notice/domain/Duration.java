package underdogs.devbie.notice.domain;

import java.time.LocalDateTime;

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
}
