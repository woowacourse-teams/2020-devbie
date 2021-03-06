package underdogs.devbie.notice.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class NoticeDescription {

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "notice_id"), name = "language")
    private Set<Language> languages;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private ApplyUrl applyUrl;

    public NoticeDescription(Set<Language> languages, String content, String applyUrl) {
        validateParameters(languages, content);
        this.languages = new HashSet<>(languages);
        this.content = content;
        this.applyUrl = new ApplyUrl(applyUrl);
    }

    private void validateParameters(Set<Language> languages, String description) {
        if (Objects.isNull(languages)
            || languages.isEmpty()
            || Objects.isNull(description)
            || description.trim().isEmpty()) {
            throw new CreateFailException();
        }
    }
}
