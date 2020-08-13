package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JobPositionTest {

    @DisplayName("공고 포지션과 이름 모두 조회")
    @Test
    void getAllJobPositionWithName() {
        Map<String, String> positions = JobPosition.getAllJobPositionWithName();

        assertAll(
            () -> assertThat(positions).containsEntry(JobPosition.FRONTEND.name(), JobPosition.FRONTEND.getName()),
            () -> assertThat(positions).containsEntry(JobPosition.BACKEND.name(), JobPosition.BACKEND.getName())
        );
    }
}