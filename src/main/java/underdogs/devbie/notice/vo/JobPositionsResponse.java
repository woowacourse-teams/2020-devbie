package underdogs.devbie.notice.vo;

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

    private static final JobPositionsResponse jobPositionsResponse = Arrays.stream(JobPosition.values())
        .map(JobPositionPair::from)
        .collect(collectingAndThen(toList(), JobPositionsResponse::new));

    private List<JobPositionPair> jobPositions;

    public static JobPositionsResponse get() {
        return jobPositionsResponse;
    }
}
