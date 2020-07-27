package underdogs.devbie.notice.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

public class NoticeAcceptanceTest extends AcceptanceTest {
    private static final Duration updatedDuration = new Duration(LocalDateTime.now(), LocalDateTime.now());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${security.jwt.token.secret-key:sample}")
    String secretKey;

    @Value("${security.jwt.token.expire-length:300}")
    long validityInMillisecond;

    private String token;

    /*
    Feature: 공고 관리

     Scenario: 공고를 관리한다.
         When 공고 1개를 추가 요청한다.
         Then 공고가 업로드 되었다.

         When 공고 전체를 조회 요청한다.
         Then 공고 전체가 조회된다.

         When 공고를 수정한다.
         Then 공고가 수정 되었다.

         When 공고를 삭제한다.
         Then 공고가 삭제되었다.
     */
    @DisplayName("공고 인수테스트")
    @Test
    void notice() throws JsonProcessingException {
        NoticeCreateRequest noticeCreateRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.of(2020, 7, 7, 10, 10)))
            .endDate(String.valueOf(LocalDateTime.of(2020, 7, 11, 10, 10)))
            .build();

        NoticeUpdateRequest noticeUpdateRequest = NoticeUpdateRequest.builder()
            .name("bossdog")
            .salary(60_000_000)
            .languages(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName(), Language.CPP.getName()))
            .jobPosition(JobPosition.FRONTEND)
            .image("/static/image/bossdog")
            .description("You are hired!")
            .startDate(updatedDuration.getStartDate().toString())
            .endDate(updatedDuration.getEndDate().toString())
            .build();

        createNotice(noticeCreateRequest);
        readAllNotice();
        updateNotice(noticeUpdateRequest);
        readNoticeDetail();
        deleteNotice();
    }

    private void createNotice(NoticeCreateRequest noticeCreateRequest) throws JsonProcessingException {
        post("/api/notices", objectMapper.writeValueAsString(noticeCreateRequest));
    }

    private void updateNotice(NoticeUpdateRequest noticeUpdateRequest) throws JsonProcessingException {
        patch("/api/notices/1", objectMapper.writeValueAsString(noticeUpdateRequest));
    }

    private void deleteNotice() {
        delete("/api/notices/1");
    }

    private void readAllNotice() {
        NoticeResponses noticeResponses = get("/api/notices", NoticeResponses.class);

        NoticeResponse noticeResponse = noticeResponses.getNoticeResponses().get(0);

        assertAll(
            () -> assertThat(noticeResponse.getId()).isEqualTo(1L),
            () -> assertThat(noticeResponse.getName()).isEqualTo("underdogs"),
            () -> assertThat(noticeResponse.getImage()).isEqualTo("/static/image/underdogs"),
            () -> assertThat(noticeResponse.getJobPosition()).isEqualTo(JobPosition.BACKEND),
            () -> assertThat(noticeResponse.getLanguages()).contains(Language.JAVA.getName(),
                Language.JAVASCRIPT.getName())
        );
    }

    private void readNoticeDetail() {
        NoticeDetailResponse result = get("/api/notices/1", NoticeDetailResponse.class);

        assertAll(
            () -> assertThat(result.getId()).isEqualTo(1L),
            () -> assertThat(result.getCompany()).isEqualTo(new Company("bossdog", 60_000_000)),
            () -> assertThat(result.getJobPosition()).isEqualTo(JobPosition.FRONTEND),
            () -> assertThat(result.getImage()).isEqualTo("/static/image/bossdog"),
            () -> assertThat(result.getNoticeDescription().getContent()).isEqualTo("You are hired!"),
            () -> assertThat(result.getNoticeDescription().getLanguages()).contains(Language.JAVA.getName(),
                Language.JAVASCRIPT.getName(), Language.CPP.getName())
        );
    }
}
