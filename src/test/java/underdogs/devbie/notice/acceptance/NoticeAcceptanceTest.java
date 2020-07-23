package underdogs.devbie.notice.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class NoticeAcceptanceTest {
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
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();

        noticeUpdateRequest = NoticeUpdateRequest.builder()
            .name("underdogs")
            .salary(60_000_000)
            .languages(Arrays.asList("java", "javascript", "C++"))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("You are hired!")
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();
    }

    void createNotice() {
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

    void updateNotice() {
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

    void deleteNotice() {
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

    void readAllNotice() {
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
}
