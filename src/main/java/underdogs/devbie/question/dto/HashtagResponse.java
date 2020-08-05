package underdogs.devbie.question.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.QuestionHashtag;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class HashtagResponse {

    private Long id;
    private String tagName;

    public static HashtagResponse from(Hashtag hashtag) {
        return HashtagResponse.builder()
            .id(hashtag.getId())
            .tagName(hashtag.getTagName().getName())
            .build();
    }

    public static List<HashtagResponse> listFrom(Set<QuestionHashtag> hashtags) {
        return hashtags.stream()
            .map(h -> HashtagResponse.from(Hashtag.builder()
                .id(h.getHashtag().getId())
                .tagName(h.getHashtag().getTagName())
                .build()))
            .collect(Collectors.toList());
    }
}
