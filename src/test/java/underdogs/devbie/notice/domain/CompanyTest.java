package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.CreateFailException;

class CompanyTest {

    @DisplayName("Company 생성 테스트 - 빈 이름 입력시 예외 발생")
    @Test
    void constructorWithEmptyName() {
        assertThatThrownBy(() -> new Company(""))
            .isInstanceOf(CreateFailException.class);
    }
}
