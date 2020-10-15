package underdogs.devbie.question.dto;

import javax.validation.constraints.NotBlank;

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
public class HashtagUpdateRequest {

    @NotBlank
    private String tagName;

    public Hashtag toEntity() {
        return new Hashtag(null, TagName.from(tagName));
    }
}
