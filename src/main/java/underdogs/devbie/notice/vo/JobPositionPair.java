package underdogs.devbie.notice.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.JobPosition;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class JobPositionPair {

    private Map<String, String> pair;

    public static JobPositionPair from(JobPosition jobPosition) {
        Map<String, String> map = new HashMap<>();

        map.put("key", jobPosition.name());
        map.put("text", jobPosition.getText());

        return new JobPositionPair(map);
    }
}
