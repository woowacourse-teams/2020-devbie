package underdogs.devbie.question.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class QuestionHashtags {

    private Set<QuestionHashtag> questionHashtags;

    public List<Long> findDeleteTargetIds(List<QuestionHashtag> allByQuestionId) {
        List<Long> questionHashtagsIds = questionHashtags.stream()
            .map(qh -> qh.getHashtag().getId())
            .collect(Collectors.toList());

        return allByQuestionId.stream()
            .map(qh -> qh.getHashtag().getId())
            .filter(id -> !questionHashtagsIds.contains(id))
            .collect(Collectors.toList());
    }
}
