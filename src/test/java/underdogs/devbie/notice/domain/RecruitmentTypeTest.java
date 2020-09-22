package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RecruitmentTypeTest {

    @DisplayName("채용타입이 상시채용인지 확인")
    @ParameterizedTest
    @CsvSource(value = {"ANY,true", "OPEN,false"})
    void isAnyTimeRecruitment(RecruitmentType recruitmentType, boolean expect) {
        assertThat(recruitmentType.isAnyTimeRecruitment())
            .isEqualTo(expect);
    }
}