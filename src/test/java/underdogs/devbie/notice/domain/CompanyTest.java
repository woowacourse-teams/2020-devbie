package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.notice.expception.CreateFailException;

class CompanyTest {

    @DisplayName("Company 생성 테스트 - 빈 이름 입력시 예외 발생")
    @Test
    void constructorWithEmptyName() {
        assertThatThrownBy(() -> new Company("", 2000))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Company 생성 테스트 - 잘못된 급여 입력시 예외 발생")
    @Test
    void constructorWithLowerThanMinimumSalary() {
        assertThatThrownBy(() -> new Company("Woowahan", 0))
            .isInstanceOf(CreateFailException.class);
    }
}
