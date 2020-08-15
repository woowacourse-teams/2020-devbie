package underdogs.devbie.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JobPosition {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String text;
}
