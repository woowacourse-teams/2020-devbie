package underdogs.devbie.question.dto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtags;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class HashtagResponse {

    private Long id;
    private String tagName;

    public static HashtagResponse from(Hashtag hashtag) {
        return HashtagResponse.builder()
            .id(hashtag.getId())
            .tagName(hashtag.getTagName().getName())
            .build();
    }

    public static List<HashtagResponse> listFrom(QuestionHashtags hashtags) {
        return hashtags.getQuestionHashtags()
            .stream()
            .map(h -> HashtagResponse.from(createHashtag(h)))
            .sorted(Comparator.comparingLong(HashtagResponse::getId))
            .collect(Collectors.toList());
    }

    private static Hashtag createHashtag(QuestionHashtag h) {
        return Hashtag.builder()
            .id(h.getHashtag().getId())
            .tagName(h.getHashtag().getTagName())
            .build();
    }
}
