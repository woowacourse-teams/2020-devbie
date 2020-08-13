package underdogs.devbie.notice.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobPositionsResponse {

    private Map<String, String> jobPositions;

    public static JobPositionsResponse from(Map<String, String> allJobPositionWithName) {
        return new JobPositionsResponse(new HashMap<>(allJobPositionWithName));
    }
}
