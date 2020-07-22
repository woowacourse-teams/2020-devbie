package underdogs.devbie.recommendation.domain;

public enum RecommendationType {
    RECOMMENDED, NON_RECOMMENDED;

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