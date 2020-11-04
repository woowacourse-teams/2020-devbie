package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VisitsTest {

    @DisplayName("Visits - 초기 Visits 생성")
    @Test
    void init() {
        Visits initialVisits = Visits.init();

        assertThat(initialVisits.getVisitCount()).isEqualTo(0L);
    }

    @DisplayName("Visits - value 증가")
    @Test
    void increase() {
        Visits initialVisits = Visits.init();
        initialVisits.increase();

        assertThat(initialVisits.getVisitCount()).isEqualTo(1L);
    }

    @DisplayName("Visits - equals")
    @Test
    void testEquals() {
        Visits initialVisits = Visits.init();
        Visits anotherVisits = Visits.init();

        assertThat(initialVisits).isEqualTo(anotherVisits);
    }
}
