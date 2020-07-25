package underdogs.devbie.acceptance;

import static underdogs.devbie.MvcTest.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.jwt.JwtTokenProvider;
import underdogs.devbie.user.domain.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    protected int port;

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @Value("${security.jwt.token.secret-key:sample}")
    private String secret;

    @Value("${security.jwt.token.expire-length:300}")
    private long seconds;

    protected String bearerToken;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        UserTokenDto userTokenDto = UserTokenDto.from(User.builder().id(1L).build());
        bearerToken = new JwtTokenProvider(secret, seconds).createToken(userTokenDto);
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

    protected <T> void post(String path, String inputJson, String bearerToken) {
        // @formatter:off
        given().
                header(AUTH_HEADER, bearerToken).
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

    protected <T> T get(String path, Class<T> responseType, String beaerToken) {
        // @formatter:off
        return
            given().
                    header(AUTH_HEADER, beaerToken).
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

    protected <T> List<T> getAll(String path, Class<T> responseType, String bearerToken) {
        // @formatter:off
        return
            given().
                    header(AUTH_HEADER, bearerToken).
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

    protected <T> void put(String path, String inputJson, String bearerToken) {
        // @formatter:off
        given().
                header(AUTH_HEADER, bearerToken).
                body(inputJson).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                put(path).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }

    protected <T> void patch(String path, String inputJson, String bearerToken) {
        // @formatter:off
        given().
                header(AUTH_HEADER, bearerToken).
                body(inputJson).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                patch(path).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }

    protected <T> void delete(String path, String bearerToken) {
        // @formatter:off
        given().
                header(AUTH_HEADER, bearerToken).
        when().
                delete(path).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }
}
