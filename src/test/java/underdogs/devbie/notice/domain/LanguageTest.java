package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.notice.exception.NoSuchLanguageException;

class LanguageTest {

    @DisplayName("등록되지 않은 언어 예외 발생")
    @Test
    void from() {
        assertThatThrownBy(() -> Language.from("KOTLIN"))
            .isInstanceOf(NoSuchLanguageException.class)
            .hasMessageContaining("존재하지 않는 프로그래밍 언어 입니다.");
    }
}
