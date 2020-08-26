package underdogs.devbie.acceptance;

import static underdogs.devbie.question.acceptance.QuestionAcceptanceTest.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.jwt.JwtTokenProvider;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.user.domain.RoleType;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    protected Long userId;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    protected int port;

    protected String bearerToken;

    @Value("${security.jwt.token.secret-key:sample}")
    private String secret;

    @Value("${security.jwt.token.expire-length:300000}")
    private long seconds;

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        RestAssured.port = port;

        userId = createUser();
        UserTokenDto userTokenDto = UserTokenDto.from(User.builder()
            .id(userId)
            .roleType(RoleType.ADMIN)
            .build());
        bearerToken = new JwtTokenProvider(secret, seconds).createToken(userTokenDto);
    }

    @AfterEach
    void tearDown() {
        deleteUser();
    }

    private Long createUser() throws JsonProcessingException {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
            .name("bsdg")
            .email("atdd@atdd.com")
            .build();
        return post("/api/users", objectMapper.writeValueAsString(userCreateRequest), Long.class);
    }

    private void deleteUser() {
        delete("/api/users/" + userId);
    }

    protected void createQuestion(String title) throws JsonProcessingException {
        QuestionCreateRequest createRequest = QuestionCreateRequest.builder()
            .title(title)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(Sets.newSet("java", "network"))
            .build();
        String inputJsonForCreate = objectMapper.writeValueAsString(createRequest);
        post("/api/questions", inputJsonForCreate);
    }

    protected QuestionResponse fetchFirstQuestion() {
        QuestionResponses questions = get("/api/questions?page=1&orderBy=CREATED_DATE&title=&content=", QuestionResponses.class);
        return questions.getQuestions().get(0);
    }

    protected <T> void post(String path, String inputJson) {
        // @formatter:off
        given().
            auth().oauth2(bearerToken).
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

    protected <T> T post(String path, String inputJson, Class<T> responseType) {
        // @formatter:off
        return
            given().
                body(inputJson).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
            when().
                post(path).
            then().
                log().all().
                statusCode(HttpStatus.CREATED.value()).
                extract().as(responseType);
        // @formatter:on
    }

    protected <T> T get(String path, Class<T> responseType) {
        // @formatter:off
        return
            given().
                auth().oauth2(bearerToken).
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
                auth().oauth2(bearerToken).
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
            auth().oauth2(bearerToken).
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

    protected <T> void patch(String path, String inputJson) {
        // @formatter:off
        given().
            auth().oauth2(bearerToken).
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

    protected <T> void delete(String path) {
        // @formatter:off
        given().
            auth().oauth2(bearerToken).
        when().
            delete(path).
        then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }
}
