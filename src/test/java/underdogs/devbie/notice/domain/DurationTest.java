package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class DurationTest {

    @DisplayName("Duration 생성 테스트 - 잘못된 날짜 입력시 예외 발생")
    @Test
    void constructorInvalidDate() {
        assertThatThrownBy(() ->
            new Duration(
                RecruitmentType.OPEN,
                null, null
            ))
            .isInstanceOf(CreateFailException.class);
    }
}
