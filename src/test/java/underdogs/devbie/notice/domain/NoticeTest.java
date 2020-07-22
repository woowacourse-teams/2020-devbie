package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NoticeTest {

    @DisplayName("공고 업데이트")
    @Test
    void update() {
        Set<String> languages = Stream.of("java", "javascript")
            .collect(Collectors.toSet());
        Notice notice = Notice.builder()
            .id(1L)
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDetail(new NoticeDetail(languages, "We are hiring!"))
            .image("/static/image/underdogs")
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        final Company expectedCompany = new Company("bossdog", 45_000_000);
        NoticeDetail expectedDetail = new NoticeDetail(languages, "You are hired");
        String expectedImage = "/static/image/bossdog";
        Notice updatedNotice = Notice.builder()
            .id(1L)
            .company(expectedCompany)
            .jobPosition(JobPosition.BACKEND)
            .noticeDetail(expectedDetail)
            .image(expectedImage)
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        notice.update(updatedNotice);

        assertAll(
            () -> assertThat(notice.getCompany()).isEqualTo(expectedCompany),
            () -> assertThat(notice.getNoticeDetail()).isEqualTo(expectedDetail),
            () -> assertThat(notice.getImage()).isEqualTo(expectedImage)

        );

    }
}
