package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class ApplyUrlTest {

    @DisplayName("Company 생성 테스트 - 잘못된 형식의 URL 입력")
    @Test
    void constructorWithWrongUrl() {
        assertThatThrownBy(() -> new ApplyUrl("잘못된URL.com"))
            .isInstanceOf(CreateFailException.class);
    }
}