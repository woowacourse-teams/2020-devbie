package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class DurationTest {

    @DisplayName("Duration 생성 테스트 - 공개채용시 날짜가 null인 경우")
    @Test
    void constructorInvalidNullData() {
        assertThatThrownBy(() ->
            new Duration(
                RecruitmentType.OPEN,
                null, null
            ))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Duration 생성 테스트 - 공개채용시 마감일자가 모집일자가 빠른 경우")
    @Test
    void constructorInvalidDate() {
        assertThatThrownBy(() ->
            new Duration(
                RecruitmentType.OPEN,
                LocalDate.now(), LocalDate.now().minusDays(1)
            ))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("Duration 비교 연산 테스트")
    @Test
    void equals() {
        Duration duration1 = new Duration(
            LocalDateTime.of(2020, 5, 5, 0, 0),
            LocalDateTime.of(2020, 5, 10, 0, 0)
        );
        Duration duration2 = new Duration(
            LocalDateTime.of(2020, 5, 5, 0, 0),
            LocalDateTime.of(2020, 5, 10, 0, 0)
        );

        assertThat(duration1).isEqualTo(duration2);

    }
}
