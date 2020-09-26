package underdogs.devbie.question.domain;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class QuestionHashtags {

    @OneToMany(fetch = LAZY, cascade = ALL, orphanRemoval = true, mappedBy = "question")
    private Set<QuestionHashtag> questionHashtags = new LinkedHashSet<>();

    public static QuestionHashtags from(Set<QuestionHashtag> questionHashtags) {
        return new QuestionHashtags(questionHashtags);
    }

    public List<Long> findDeleteTargetIds(List<QuestionHashtag> allByQuestionId) {
        List<Long> questionHashtagsIds = questionHashtags.stream()
            .map(qh -> qh.getHashtag().getId())
            .collect(Collectors.toList());

        return allByQuestionId.stream()
            .map(qh -> qh.getHashtag().getId())
            .filter(id -> !questionHashtagsIds.contains(id))
            .collect(Collectors.toList());
    }

    public void setHashtags(Set<QuestionHashtag> questionHashtags) {
        this.questionHashtags.clear();
        this.questionHashtags.addAll(questionHashtags);
    }
}
