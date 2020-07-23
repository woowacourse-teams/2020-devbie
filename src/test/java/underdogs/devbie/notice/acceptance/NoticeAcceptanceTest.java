package underdogs.devbie.notice.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoticeAcceptanceTest {
    private static final Duration updatedDuration = new Duration(LocalDateTime.now(), LocalDateTime.now());

    @LocalServerPort
    public int port;
    private NoticeCreateRequest noticeCreateRequest;
    private NoticeUpdateRequest noticeUpdateRequest;

    static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        noticeCreateRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList("java", "javascript"))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.of(2020, 7, 7, 10, 10)))
            .endDate(String.valueOf(LocalDateTime.of(2020, 7, 11, 10, 10)))
            .build();

        noticeUpdateRequest = NoticeUpdateRequest.builder()
            .name("bossdog")
            .salary(60_000_000)
            .languages(Arrays.asList("java", "javascript", "C++"))
            .jobPosition(JobPosition.FRONTEND)
            .image("/static/image/bossdog")
            .description("You are hired!")
            .startDate(updatedDuration.getStartDate().toString())
            .endDate(updatedDuration.getEndDate().toString())
            .build();
    }

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
    void notice() {
        createNotice();
        readAllNotice();
        updateNotice();
        readNoticeDetail();
        deleteNotice();
    }

    private void createNotice() {
        //@formatter:off
        given().
            body(noticeCreateRequest).
            contentType(MediaType.APPLICATION_JSON_VALUE).
        when().
            post("/api/notices").
        then().
            log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/api/notices/1");
        //@formatter:on
    }

    private void updateNotice() {
        //@formatter:off
        given().
            body(noticeUpdateRequest).
            contentType(MediaType.APPLICATION_JSON_VALUE).
        when().
            put("/api/notices/1").
        then().
            log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
        //@formatter:on
    }

    private void deleteNotice() {
        //@formatter:off
        given().
            contentType(MediaType.APPLICATION_JSON_VALUE).
        when().
            delete("/api/notices/1").
        then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
        //@formatter:on
    }

    private void readAllNotice() {
        //@formatter:off
        List<NoticeResponse> result = given().
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
        when().
            get("/api/notices").
        then().
            log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().jsonPath().getList(".", NoticeResponse.class);
        //@formatter:on

        assertThat(result).isNotEmpty();
        NoticeResponse noticeResponse = result.get(0);
        assertAll(
            () -> assertThat(noticeResponse.getId()).isEqualTo(1L),
            () -> assertThat(noticeResponse.getName()).isEqualTo("underdogs"),
            () -> assertThat(noticeResponse.getImage()).isEqualTo("/static/image/underdogs"),
            () -> assertThat(noticeResponse.getJobPosition()).isEqualTo(JobPosition.BACKEND),
            () -> assertThat(noticeResponse.getLanguages()).contains("java", "javascript")
        );
    }

    private void readNoticeDetail() {
        //@formatter:off
        NoticeDetailResponse result = given().
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            get("/api/notices/1").
            then().
            log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().as(NoticeDetailResponse.class);
        //@formatter:on

        assertAll(
            () -> assertThat(result.getId()).isEqualTo(1L),
            () -> assertThat(result.getCompany()).isEqualTo(new Company("bossdog", 60_000_000)),
            () -> assertThat(result.getJobPosition()).isEqualTo(JobPosition.FRONTEND),
            () -> assertThat(result.getImage()).isEqualTo("/static/image/bossdog"),
            () -> assertThat(result.getNoticeDetail().getDescription()).isEqualTo("You are hired!"),
            () -> assertThat(result.getNoticeDetail().getLanguages()).contains("java", "javascript", "C++")
        );
    }
}
