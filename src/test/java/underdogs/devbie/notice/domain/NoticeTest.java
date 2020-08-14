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
        Set<Language> languages = Stream.of(Language.JAVA, Language.JAVASCRIPT)
            .collect(Collectors.toSet());
        Notice notice = Notice.builder()
            .id(1L)
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!"))
            .image("/static/image/underdogs")
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        final Company expectedCompany = new Company("bossdog", 45_000_000);
        NoticeDescription expectedDetail = new NoticeDescription(languages, "You are hired");
        String expectedImage = "/static/image/bossdog";
        JobPosition expectedJobPosition = JobPosition.FRONTEND;
        Notice updatedNotice = Notice.builder()
            .id(1L)
            .title("우테코 모집")
            .noticeType(NoticeType.EDUCATION)
            .company(expectedCompany)
            .noticeDescription(expectedDetail)
            .image(expectedImage)
            .jobPosition(expectedJobPosition)
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        notice.update(updatedNotice);

        assertAll(
            () -> assertThat(notice.getCompany()).isEqualTo(expectedCompany),
            () -> assertThat(notice.getNoticeDescription()).isEqualTo(expectedDetail),
            () -> assertThat(notice.getImage()).isEqualTo(expectedImage)
        );
    }
}
