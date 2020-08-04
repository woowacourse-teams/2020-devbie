package underdogs.devbie.question.dto;

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
public class HashtagResponse {

    private Long id;
    private String tagName;

    public static HashtagResponse from(Hashtag hashtag) {
        return HashtagResponse.builder()
            .id(hashtag.getId())
            .tagName(hashtag.getTagName().getName())
            .build();
    }
}
