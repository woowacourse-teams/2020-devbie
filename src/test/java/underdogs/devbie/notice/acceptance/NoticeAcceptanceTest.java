package underdogs.devbie.notice.acceptance;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import underdogs.devbie.notice.domain.JobPosition;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class NoticeAcceptanceTest {

    @LocalServerPort
    public int port;

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    void createNotice() {
        Map<String, Object> params = new HashMap<>();

        params.put("startDate", String.valueOf(LocalDateTime.now()));
        params.put("endDate", String.valueOf(LocalDateTime.now()));
        params.put("name", "underdogs");
        params.put("salary", "50000000");
        params.put("languages", Arrays.asList("java","javascript"));
        params.put("jobPosition", JobPosition.BACKEND.name());
        params.put("image", "/static/image/underdogs");
        params.put("description", "We are hiring!");

        //@formatter:off
        given().
            body(params).
            contentType(MediaType.APPLICATION_JSON_VALUE).
        when().
            post("/api/notices").
        then().
            log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/api/notices/1");
        //@formatter:on
    }
}
