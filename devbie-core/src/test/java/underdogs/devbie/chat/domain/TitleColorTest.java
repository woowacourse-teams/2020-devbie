package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.chat.exception.NoSuchTitleColorException;

class TitleColorTest {

    @DisplayName("지원하지 않는 타이틀 컬러")
    @Test
    void from() {
        assertThatThrownBy(() -> TitleColor.from("#9E9E9A"))
            .isInstanceOf(NoSuchTitleColorException.class)
            .hasMessageContaining("존재하지 않는 TitleColor 입니다.");
    }
}
