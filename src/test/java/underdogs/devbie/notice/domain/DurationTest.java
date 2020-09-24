package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
            RecruitmentType.OPEN,
            LocalDate.of(2020, 5, 5),
            LocalDate.of(2020, 5, 10)
        );
        Duration duration2 = new Duration(
            RecruitmentType.OPEN,
            LocalDate.of(2020, 5, 5),
            LocalDate.of(2020, 5, 10)
        );

        assertThat(duration1).isEqualTo(duration2);

    }

    @DisplayName("마감된 채용 확인 - 수시채용시 항상 마감되지 않음")
    @Test
    void isFinishedByAnyTime() {
        Duration duration = new Duration(RecruitmentType.ANY, null, null);

        assertThat(duration.isFinished()).isFalse();
    }

    @DisplayName("마감된 채용 확인 - 공개채용시 마감날짜와 비교")
    @CsvSource(value = {"-10,true", "10,false"})
    @ParameterizedTest
    void isFinishedByOpenRecruitment(long weight, boolean expect) {
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now().plusDays(weight);

        System.out.println("현재날짜" + LocalDate.now());
        System.out.println("채용가마감날짜" + endDate);

        Duration duration = new Duration(RecruitmentType.OPEN, startDate, endDate);

        assertThat(duration.isFinished()).isEqualTo(expect);
    }
}
