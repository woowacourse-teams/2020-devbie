package underdogs.devbie.notice.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeType;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

@Sql(value = "/notice_create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/notice_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

         When 공고 전체를 공고타입별 조회 요청한다.
         Then 공고 전체가 조회된다.

         When 공고 전체를 공고타입과 포지션 조회 요청한다.
         hen 공고 전체가 조회된다.

         When 공고 전체를 공고타입, 포지션, 언어별 조회 요청한다.
         Then 공고 전체가 조회된다.

         When 공고를 수정한다.
         Then 공고가 수정 되었다.

         When 공고를 삭제한다.
         Then 공고가 삭제되었다.
     */
    @DisplayName("공고 인수테스트")
    @TestFactory
    Stream<DynamicTest> notice() {
        return Stream.of(
            dynamicTest("공고 게시글을 생성한다.", () -> {
                NoticeCreateRequest noticeCreateRequest = NoticeCreateRequest.builder()
                    .name("underdogs")
                    .title("언더독스 채용")
                    .noticeType(NoticeType.JOB)
                    .salary(50_000_000)
                    .languages(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName()))
                    .jobPosition(JobPosition.BACKEND)
                    .image("/static/image/underdogs")
                    .description("We are hiring!")
                    .startDate(String.valueOf(LocalDateTime.of(2020, 7, 7, 10, 10)))
                    .endDate(String.valueOf(LocalDateTime.of(2020, 7, 11, 10, 10)))
                    .build();

                post("/api/notices", objectMapper.writeValueAsString(noticeCreateRequest));
            }),
            dynamicTest("채용공고 게시글 전체를 조회한다.", () -> {
                NoticeResponses noticeResponses = get("/api/notices?noticeType=JOB", NoticeResponses.class);

                assertThat(noticeResponses.getNoticeResponses())
                    .extracting(NoticeResponse::getNoticeType)
                    .contains(NoticeType.JOB);
            }),
            dynamicTest("채용공고와 포지션 별 게시글 전체를 조회한다.", () -> {
                NoticeResponses noticeResponses = get("/api/notices?noticeType=JOB&jobPosition=BACKEND",
                    NoticeResponses.class);

                List<NoticeResponse> response = noticeResponses.getNoticeResponses();
                assertAll(
                    () -> assertThat(response)
                        .extracting(NoticeResponse::getNoticeType)
                        .contains(NoticeType.JOB),

                    () -> assertThat(response)
                        .extracting(NoticeResponse::getJobPosition)
                        .contains(JobPosition.BACKEND)
                );

            }),
            dynamicTest("채용공고와 포지션과 언어별 게시글 전체를 조회한다.", () -> {
                NoticeResponses noticeResponses = get("/api/notices?noticeType=JOB&jobPosition=BACKEND&language=CPP",
                    NoticeResponses.class);

                List<NoticeResponse> response = noticeResponses.getNoticeResponses();
                assertAll(
                    () -> assertThat(response)
                        .extracting(NoticeResponse::getNoticeType)
                        .contains(NoticeType.JOB),

                    () -> assertThat(response)
                        .extracting(NoticeResponse::getJobPosition)
                        .contains(JobPosition.BACKEND),

                    () -> assertThat(response)
                        .extracting(NoticeResponse::getLanguages)
                        .anyMatch(language -> language.contains(Language.CPP.getName()))
                );

            }),
            dynamicTest("공고 게시글을 수정한다.", () -> {
                NoticeUpdateRequest noticeUpdateRequest = NoticeUpdateRequest.builder()
                    .name("bossdog")
                    .title("우테코 모집")
                    .noticeType(NoticeType.EDUCATION)
                    .salary(60_000_000)
                    .languages(
                        Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName(), Language.CPP.getName()))
                    .jobPosition(JobPosition.FRONTEND)
                    .image("/static/image/bossdog")
                    .description("You are hired!")
                    .startDate(updatedDuration.getStartDate().toString())
                    .endDate(updatedDuration.getEndDate().toString())
                    .build();

                patch("/api/notices/1", objectMapper.writeValueAsString(noticeUpdateRequest));
            }),
            dynamicTest("공고 게시글 하나르 상세 조회한다.", () -> {
                NoticeDetailResponse result = get("/api/notices/1", NoticeDetailResponse.class);

                assertAll(
                    () -> assertThat(result.getId()).isEqualTo(1L),
                    () -> assertThat(result.getCompany()).isEqualTo(new Company("bossdog", 60_000_000)),
                    () -> assertThat(result.getTitle()).isEqualTo("우테코 모집"),
                    () -> assertThat(result.getNoticeType()).isEqualTo(NoticeType.EDUCATION),
                    () -> assertThat(result.getJobPosition()).isEqualTo(JobPosition.FRONTEND),
                    () -> assertThat(result.getImage()).isEqualTo("/static/image/bossdog"),
                    () -> assertThat(result.getNoticeDescription().getContent()).isEqualTo("You are hired!"),
                    () -> assertThat(result.getNoticeDescription().getLanguages()).contains(Language.JAVA.getName(),
                        Language.JAVASCRIPT.getName(), Language.CPP.getName())
                );
            }),
            dynamicTest("공고 게시글을 삭제한다.", () -> {
                delete("/api/notices/1");
            })
        );
    }
}
