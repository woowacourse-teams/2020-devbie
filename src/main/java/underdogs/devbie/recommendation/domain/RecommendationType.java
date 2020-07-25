package underdogs.devbie.recommendation.domain;

import java.util.Arrays;

public enum RecommendationType {

    RECOMMENDED,
    NON_RECOMMENDED;

    public static RecommendationType from(String recommendationType) {
        return Arrays.stream(values())
            .filter(type -> type.name().equals(recommendationType))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean is(RecommendationType recommendationType) {
        return this == recommendationType;
    }

    public RecommendationType toggleType() {
        if (this == RECOMMENDED) {
            return NON_RECOMMENDED;
        }
        return RECOMMENDED;
    }
}
