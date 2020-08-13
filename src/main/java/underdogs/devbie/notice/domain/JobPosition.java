package underdogs.devbie.notice.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JobPosition {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String name;

    public static Map<String, String> getAllJobPositionWithName() {
        return Arrays.stream(values())
            .collect(Collectors.toMap(JobPosition::name, JobPosition::getName));
    }
}
