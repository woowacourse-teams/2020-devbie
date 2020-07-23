package underdogs.devbie.notice.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.expception.CreateFailException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@EqualsAndHashCode
public class NoticeDetail {

    private static final String EMPTY_STRING = "";
    @Embedded
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "notice_id"), name = "language")
    private Set<String> languages;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String description;

    public NoticeDetail(Set<String> languages, String description) {
        validateParameters(languages, description);
        this.languages = new HashSet<>(languages);
        this.description = description;
    }

    private void validateParameters(Set<String> languages, String description) {
        if (Objects.isNull(languages)
            || languages.isEmpty()
            || Objects.isNull(description)
            || description.trim().equals(EMPTY_STRING)) {
            throw new CreateFailException();
        }
    }
}
