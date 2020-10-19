package underdogs.devbie.answer.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.answer.AnswerController;
import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;
import underdogs.devbie.answer.domain.Answers;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.dto.AnswerUpdateRequest;
import underdogs.devbie.answer.service.AnswerService;
import underdogs.devbie.auth.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.resolver.LoginUserArgumentResolver;
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
            .name("TEST_NAME")
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
        AnswerCreateRequest answerCreateRequest = AnswerCreateRequest.of(1L, TEST_ANSWER_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);
        given(answerService.save(user, answerCreateRequest)).willReturn(1L);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 Question Id")
    @Test
    void saveWithInvalidQuestionId() throws Exception {
        AnswerCreateRequest answerCreateRequest = AnswerCreateRequest.of(null, TEST_ANSWER_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isMethodNotAllowed())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 빈 Content")
    @Test
    void saveWithEmptyContent() throws Exception {
        String EMPTY_CONTENT = "";
        AnswerCreateRequest answerCreateRequest = AnswerCreateRequest.of(1L, EMPTY_CONTENT);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isMethodNotAllowed())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 Answer 저장 시 예외 발생 - 유효 하지 않은 Null Content")
    @Test
    void saveWithNullContent() throws Exception {
        AnswerCreateRequest answerCreateRequest = AnswerCreateRequest.of(1L, null);

        String inputString = OBJECT_MAPPER.writeValueAsString(answerCreateRequest);

        postAction("/api/answers", inputString, TEST_TOKEN)
            .andExpect(status().isMethodNotAllowed())
            .andDo(print());
    }

    @DisplayName("Answer id로 하나의 Answer 조회")
    @Test
    void read() throws Exception {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(2L)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();

        given(answerService.read(anyLong())).willReturn(AnswerResponse.of(expectAnswer, user));

        MvcResult mvcResult = getAction(String.format("/api/answers/%d", 1L)).andReturn();

        AnswerResponse answerResponse = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
            AnswerResponse.class);

        assertThat(answerResponse).isNotNull();
        assertAll(
            () -> assertThat(answerResponse.getId()).isEqualTo(1L),
            () -> assertThat(answerResponse.getAuthor()).isEqualTo("TEST_NAME"),
            () -> assertThat(answerResponse.getQuestionId()).isEqualTo(3L),
            () -> assertThat(answerResponse.getContent()).isEqualTo(TEST_ANSWER_CONTENT)
        );
    }

    @DisplayName("Answer 수정")
    @Test
    void update() throws Exception {
        AnswerUpdateRequest answerUpdateRequest = AnswerUpdateRequest.from("Changed Request");
        willDoNothing().given(answerService).update(any(User.class), anyLong(), any(AnswerUpdateRequest.class));

        patchAction(String.format("/api/answers/%d", 1L), OBJECT_MAPPER.writeValueAsString(answerUpdateRequest),
            TEST_TOKEN);

        verify(answerService).update(any(User.class), eq(1L), any(AnswerUpdateRequest.class));
    }

    @DisplayName("Answer 삭제")
    @Test
    void delete() throws Exception {
        willDoNothing().given(answerService).delete(any(User.class), anyLong());

        deleteAction(String.format("/api/answers/%d", 1L), TEST_TOKEN);

        verify(answerService).delete(any(User.class), eq(1L));
    }

    @DisplayName("QuestionId로 Answer들 가져오기")
    @Test
    void searchByQuestionId() throws Exception {
        Answer expectedAnswerItem = Answer.builder()
            .userId(1L)
            .questionId(1L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();

        List<User> users = Lists.newArrayList(user);
        AnswerResponses expected = AnswerResponses.of(Answers.from(Collections.singletonList(expectedAnswerItem)),
            users);
        given(answerService.readByQuestionId(expectedAnswerItem.getQuestionId())).willReturn(expected);
        MvcResult result = getAction(String.format("/api/answers?questionId=%d", expectedAnswerItem.getQuestionId()))
            .andExpect(status().isOk())
            .andReturn();

        String response = result.getResponse().getContentAsString();
        AnswerResponses answerResponses = OBJECT_MAPPER.readValue(response, AnswerResponses.class);

        assertAll(
            () -> assertThat(answerResponses).isNotNull(),
            () -> assertThat(answerResponses.getAnswerResponses()).isNotEmpty(),
            () -> assertThat(answerResponses.getAnswerResponses().get(0).getQuestionId())
                .isEqualTo(expectedAnswerItem.getQuestionId())
        );
    }
}
