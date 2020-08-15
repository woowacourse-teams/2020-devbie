package underdogs.devbie.notice.dto;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.JobPosition;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobPositionsResponse {

    private List<JobPositionPairDto> jobPositions;

    public static JobPositionsResponse from() {
        return Arrays.stream(JobPosition.values())
            .map(JobPositionPairDto::from)
            .collect(collectingAndThen(toList(), JobPositionsResponse::new));
    }
}
