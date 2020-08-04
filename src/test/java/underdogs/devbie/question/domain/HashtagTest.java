package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;

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
}