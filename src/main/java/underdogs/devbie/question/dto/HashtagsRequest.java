package underdogs.devbie.question.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.TagName;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class HashtagsRequest {

    @NotEmpty
    private Set<String> hashtags = new HashSet<>();

    public Set<Hashtag> toEntity() {
        return hashtags.stream()
            .map(h -> Hashtag.builder()
                .tagName(TagName.from(h))
                .build())
            .collect(Collectors.toSet());
    }
}
