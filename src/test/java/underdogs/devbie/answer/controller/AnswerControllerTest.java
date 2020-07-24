package underdogs.devbie.answer.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.StringContains.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;
import underdogs.devbie.answer.domain.Answers;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.service.AnswerService;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.user.domain.User;

@WebMvcTest(controllers = AnswerController.class)
public class AnswerControllerTest extends MvcTest {

    public static final String TEST_ANSWER_CONTENT = "TEST_ANSWER_CONTENT";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @MockBean
    private AnswerService answerService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .oauthId("TEST_USER")
            .email("TEST_EMAIL")
            .build();
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
    }

    @DisplayName("사용자 요청을 받아 Answer 생성")
    @Test
    void save() throws Exception {
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(1L, TEST_ANSWER_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);
        given(answerService.save(user, answerCreateRequest)).willReturn(1L);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 Question Id")
    @Test
    void save2() throws Exception {
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(null, TEST_ANSWER_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);
        given(answerService.save(user, answerCreateRequest)).willReturn(1L);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 빈 Content")
    @Test
    void saveWithInvalidContent() throws Exception {
        String EMPTY_CONTENT = "";
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(1L, EMPTY_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);
        given(answerService.save(user, answerCreateRequest)).willReturn(1L);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 Null Content")
    @Test
    void saveWithInvalidContent2() throws Exception {
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(1L, null);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);
        given(answerService.save(user, answerCreateRequest)).willReturn(1L);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("Answer 전체 조회")
    @Test
    void readAll() throws Exception {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(2L)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        AnswerResponses expectAnswers = AnswerResponses.from(
            Answers.from(Collections.singletonList(expectAnswer))
        );
        given(answerService.readAll()).willReturn(expectAnswers);

        MvcResult mvcResult = getAction("/api/answers").andReturn();

        AnswerResponses answerResponses = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
            AnswerResponses.class);

        assertThat(answerResponses).isNotNull();
        assertThat(answerResponses.getAnswerResponses()).isNotNull();
        List<AnswerResponse> actual = answerResponses.getAnswerResponses();
        assertAll(
            () -> assertEquals(actual.get(0).getId(), 1L),
            () -> assertEquals(actual.get(0).getUserId(), 2L),
            () -> assertEquals(actual.get(0).getQuestionId(), 3L),
            () -> assertEquals(actual.get(0).getContent(), TEST_ANSWER_CONTENT)
        );
    }
}
