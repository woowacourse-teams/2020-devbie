package underdogs.devbie.question.controller;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;
import static underdogs.devbie.question.acceptance.QuestionAcceptanceTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.service.QuestionService;
import underdogs.devbie.user.domain.User;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest extends MvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private QuestionService questionService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);
    }

    @DisplayName("질문 생성")
    @Test
    void save() throws Exception {
        QuestionCreateRequest request = QuestionCreateRequest.builder()
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();
        String inputJson = objectMapper.writeValueAsString(request);

        given(questionService.save(any(), any(QuestionCreateRequest.class))).willReturn(1L);

        postAction("/api/questions", inputJson, TEST_TOKEN)
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("location", "/api/questions/1"));
    }

    @DisplayName("질문 목록 조회")
    @Test
    void readAll() throws Exception {
        QuestionResponse response = QuestionResponse.builder()
            .questionId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();
        List<QuestionResponse> responses = Lists.newArrayList(response);

        given(questionService.readAll()).willReturn(responses);

        getAction("/api/questions")
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(TEST_QUESTION_TITLE)))
            .andExpect(content().string(containsString(TEST_QUESTION_CONTENT)));
    }

    @DisplayName("질문 조회")
    @Test
    void read() throws Exception {
        QuestionResponse response = QuestionResponse.builder()
            .questionId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();

        given(questionService.read(anyLong())).willReturn(response);

        getAction("/api/questions/" + response.getQuestionId())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(TEST_QUESTION_TITLE)))
            .andExpect(content().string(containsString(TEST_QUESTION_CONTENT)));
    }

    @DisplayName("질문 수정")
    @Test
    void update() throws Exception {
        QuestionUpdateRequest request = QuestionUpdateRequest.builder()
            .questionId(1L)
            .title("Changed Title")
            .content("Changed Content")
            .build();
        String inputJson = objectMapper.writeValueAsString(request);

        willDoNothing().given(questionService).update(anyLong(), anyLong(), any(QuestionUpdateRequest.class));

        patchAction("/api/questions/" + request.getQuestionId(), inputJson, TEST_TOKEN)
            .andExpect(status().isNoContent());

        verify(questionService).update(eq(1L), eq(1L), any(QuestionUpdateRequest.class));
    }

    @DisplayName("질문 삭제")
    @Test
    void delete() throws Exception {
        deleteAction("/api/questions/1", TEST_TOKEN)
            .andExpect(status().isNoContent());

        verify(questionService).delete(eq(1L), eq(1L));
    }
}