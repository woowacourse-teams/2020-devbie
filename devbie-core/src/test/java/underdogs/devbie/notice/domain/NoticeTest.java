package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

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
            .company(new Company("underdogs"))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!", "https://devbie.kr"))
            .image("/static/image/underdogs")
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
            .build();

        final Company expectedCompany = new Company("bossdog");
        NoticeDescription expectedDetail = new NoticeDescription(languages, "You are hired", "https://devbie.kr");
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
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
            .build();

        notice.update(updatedNotice);

        assertAll(
            () -> assertThat(notice.getCompany()).isEqualTo(expectedCompany),
            () -> assertThat(notice.getNoticeDescription()).isEqualTo(expectedDetail),
            () -> assertThat(notice.getImage()).isEqualTo(expectedImage)
        );
    }

    @Test
    void validate_Invalid_Parameters_Should_Throw_CreateFailException() {

        assertThatThrownBy(() -> Notice.builder()
            .id(1L)
            .title("우테코 모집")
            .noticeType(NoticeType.EDUCATION)
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
            .build())
            .isInstanceOf(CreateFailException.class)
            .hasMessageContaining("생성 인자가 올바르지 않습니다.");

    }
}
