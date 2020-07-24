package underdogs.devbie.notice.domain;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
import underdogs.devbie.notice.expception.CreateFailException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@EqualsAndHashCode
public class NoticeDescription {

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "notice_id"), name = "language")
    private Set<Language> languages;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String content;

    public NoticeDescription(Set<String> languages, String content) {
        validateParameters(languages, content);
        this.languages = languages.stream().map(Language::from).collect(Collectors.toSet());
        this.content = content;
    }

    private void validateParameters(Set<String> languages, String description) {
        if (Objects.isNull(languages)
            || languages.isEmpty()
            || Objects.isNull(description)
            || description.trim().isEmpty()) {
            throw new CreateFailException();
        }
    }
}
