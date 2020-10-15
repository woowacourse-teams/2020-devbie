package underdogs.devbie.question.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Hashtag;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class HashtagResponses {

    private List<HashtagResponse> hashtags;

    public static HashtagResponses from(List<Hashtag> hashtags) {
        return new HashtagResponses(hashtags.stream()
            .map(HashtagResponse::from)
            .collect(Collectors.toList()));
    }
}
