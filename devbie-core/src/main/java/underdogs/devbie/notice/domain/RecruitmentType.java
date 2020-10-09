package underdogs.devbie.notice.domain;

public enum RecruitmentType {

    OPEN, ANY;

    public boolean isAnyTimeRecruitment() {
        return this == ANY;
    }
}
