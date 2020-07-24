package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.notice.expception.InvalidDurationException;

class DurationTest {

    @DisplayName("Duration 생성 테스트 - 잘못된 날짜 입력시 예외 발생")
    @Test
    void constructorInvalidDate() {
        assertThatThrownBy(() -> new Duration(
            LocalDateTime.of(2020, 5, 5, 0, 0),
            LocalDateTime.of(2020, 5, 4, 0, 0)))
            .isInstanceOf(InvalidDurationException.class)
            .hasMessage("시작일은 종료일 이전이어야 합니다.");
    }
}
