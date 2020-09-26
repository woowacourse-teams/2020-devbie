package underdogs.devbie.question.domain;

import static javax.persistence.CascadeType.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.config.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Hashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TagName tagName;

    @OneToMany(mappedBy = "hashtag", cascade = ALL)
    private Set<QuestionHashtag> questionHashtags = new LinkedHashSet<>();

    @Builder
    public Hashtag(Long id, TagName tagName) {
        validateParameters(tagName);
        this.id = id;
        this.tagName = tagName;
    }

    private void validateParameters(TagName tagName) {
        if (Objects.isNull(tagName)) {
            throw new CreateFailException();
        }
    }

    public void update(Hashtag hashtag) {
        this.tagName = hashtag.getTagName();
    }
}
