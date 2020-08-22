package underdogs.devbie.question.domain;

public enum SearchScope {

    TITLE,
    CONTENT,
    BOTH;

    public boolean isTitle() {
        return this == TITLE;
    }

    public boolean isContent() {
        return this == CONTENT;
    }
}
