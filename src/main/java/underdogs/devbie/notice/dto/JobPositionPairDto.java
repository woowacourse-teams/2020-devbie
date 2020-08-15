package underdogs.devbie.notice.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.JobPosition;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobPositionPairDto {

    private Map<String, String> pair;

    public static JobPositionPairDto from(JobPosition jobPosition) {
        Map<String, String> map = new HashMap<>();

        map.put("key", jobPosition.name());
        map.put("text", jobPosition.getText());

        return new JobPositionPairDto(map);
    }
}
