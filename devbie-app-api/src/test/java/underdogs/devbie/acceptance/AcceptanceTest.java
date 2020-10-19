package underdogs.devbie.acceptance;

import static underdogs.devbie.question.acceptance.QuestionAcceptanceTest.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.jwt.JwtTokenProvider;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeType;
import underdogs.devbie.notice.domain.RecruitmentType;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.user.RoleType;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acceptance")
public abstract class AcceptanceTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected Long userId;
    @LocalServerPort
    protected int port;

    protected String bearerToken;

    @Value("${security.jwt.token.secret-key:sample}")
    private String secret;

    @Value("${security.jwt.token.expire-length:300000}")
    private long seconds;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.execute();

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

    protected Long createQuestion(String title) throws JsonProcessingException {
        QuestionCreateRequest createRequest = QuestionCreateRequest.builder()
            .title(title)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(Sets.newSet("java", "network"))
            .build();
        String inputJsonForCreate = objectMapper.writeValueAsString(createRequest);
        String id = post("/api/questions", inputJsonForCreate);
        return Long.parseLong(id);
    }

    protected QuestionResponse fetchFirstQuestion() {
        QuestionResponses questions = get("/api/questions?page=1&orderBy=CREATED_DATE&title=&content=",
            QuestionResponses.class);
        return questions.getQuestions().get(0);
    }

    protected void createNotice(String title) throws JsonProcessingException {
        NoticeCreateRequest createRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .title(title)
            .noticeType(NoticeType.JOB)
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-10")
            .endDate("2020-10-10")
            .applyUrl("https://devbie.kr")
            .recruitmentType(RecruitmentType.OPEN)
            .build();
        String inputJsonForCreate = objectMapper.writeValueAsString(createRequest);
        post("/api/notices", inputJsonForCreate);
    }

    protected <T> void post(String path) {
        // @formatter:off
        given().
            auth().oauth2(bearerToken).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            post(path).
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value());
        // @formatter:on
    }

    protected <T> String post(String path, String inputJson) {
        // @formatter:off
        String[] locations = given().
            auth().oauth2(bearerToken).
            body(inputJson).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
        when().
            post(path).
        then().
            log().all().
            statusCode(HttpStatus.CREATED.value()).
            extract().header("location").split("/");
        return locations[locations.length -1];
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
