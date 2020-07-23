package underdogs.devbie.acceptance;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    protected <T> void post(String path, String inputJson) {
        // @formatter:off
        given().
                body(inputJson).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                post(path).
        then().
                log().all().
                statusCode(HttpStatus.CREATED.value());
        // @formatter:on
    }

    protected <T> T get(String path, Class<T> responseType) {
        // @formatter:off
        return
            given().
            when().
                    get(path).
            then().
                    log().all().
                    statusCode(HttpStatus.OK.value()).
                    extract().as(responseType);
        // @formatter:on
    }

    protected <T> List<T> getAll(String path, Class<T> responseType) {
        // @formatter:off
        return
            given().
            when().
                    get(path).
            then().
                    log().all().
                    statusCode(HttpStatus.OK.value()).
                    extract().
                    jsonPath().
                    getList(".", responseType);
        // @formatter:on
    }

    protected <T> void put(String path, String inputJson) {
        // @formatter:off
        given().
                body(inputJson).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                put(path).
        then().
                log().all().
                statusCode(HttpStatus.OK.value());
        // @formatter:on
    }

    protected <T> void delete(String path) {
        // @formatter:off
        given().
        when().
                delete(path).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }
}
