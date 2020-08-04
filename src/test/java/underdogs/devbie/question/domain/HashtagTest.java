package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;
import static underdogs.devbie.question.domain.TagNameTest.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class HashtagTest {

    @DisplayName("Hashtag 빌더 테스트 - tagName이 없을 때 예외 발생")
    @Test
    void hashtagBuilderWithoutTagName() {
        assertThatThrownBy(() -> Hashtag.builder()
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Hashtag 내용 수정")
    @Test
    void updateHashtag() {
        Hashtag hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();
        Hashtag updateHashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from("Changed Name"))
            .build();

        hashtag.update(updateHashtag);

        assertThat(hashtag.getTagName().getName()).isEqualTo(updateHashtag.getTagName().getName());
    }
}