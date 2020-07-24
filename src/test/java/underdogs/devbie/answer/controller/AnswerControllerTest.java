package underdogs.devbie.answer.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
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

    @BeforeEach
    void setUp() {
        User user = User.builder()
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

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 Question Id")
    @Test
    void save2() throws Exception {
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(null, TEST_ANSWER_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);

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

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 Null Content")
    @Test
    void saveWithInvalidContent2() throws Exception {
        String EMPTY_CONTENT = "";
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(1L, null);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}
