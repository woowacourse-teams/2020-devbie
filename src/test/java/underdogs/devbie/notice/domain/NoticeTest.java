package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.notice.expception.CreateFailException;

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
        JobPosition expectedJobPosition = JobPosition.FRONTEND;
        Notice updatedNotice = Notice.builder()
            .id(1L)
            .company(expectedCompany)
            .noticeDetail(expectedDetail)
            .image(expectedImage)
            .jobPosition(expectedJobPosition)
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        notice.update(updatedNotice);

        assertAll(
            () -> assertThat(notice.getCompany()).isEqualTo(expectedCompany),
            () -> assertThat(notice.getNoticeDetail()).isEqualTo(expectedDetail),
            () -> assertThat(notice.getImage()).isEqualTo(expectedImage)
        );
    }

    @DisplayName("Notice 생성 테스트 - Image가 비었으면 예외 발생")
    @Test
    void name() {
        assertThatThrownBy(() -> Notice.builder()
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDetail(new NoticeDetail(new HashSet<>(Arrays.asList("C++")), "We are hiring!"))
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build()
        )
            .isInstanceOf(CreateFailException.class);
    }
}
