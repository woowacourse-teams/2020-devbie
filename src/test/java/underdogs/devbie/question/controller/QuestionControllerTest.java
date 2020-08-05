package underdogs.devbie.question.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;
import static underdogs.devbie.question.acceptance.QuestionAcceptanceTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionContent;
import underdogs.devbie.question.domain.QuestionTitle;
import underdogs.devbie.question.dto.HashtagsRequest;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
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

    @BeforeEach
    void setUp() {
        User user = User.builder()
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
        Question question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(QuestionTitle.from(TEST_QUESTION_TITLE))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .build();
        QuestionResponses responses = QuestionResponses.from(Lists.newArrayList(question));

        given(questionService.readAll()).willReturn(responses);

        MvcResult mvcResult = getAction("/api/questions/")
            .andExpect(status().isOk())
            .andReturn();
        String value = mvcResult.getResponse().getContentAsString();
        QuestionResponses questionResponses = objectMapper.readValue(value, QuestionResponses.class);

        assertAll(
            () -> assertThat(questionResponses.getQuestions().get(0).getTitle()).isEqualTo(TEST_QUESTION_TITLE),
            () -> assertThat(questionResponses.getQuestions().get(0).getContent()).isEqualTo(TEST_QUESTION_CONTENT)
        );
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

        MvcResult mvcResult = getAction("/api/questions/" + response.getQuestionId())
            .andExpect(status().isOk())
            .andReturn();
        String value = mvcResult.getResponse().getContentAsString();
        QuestionResponse questionResponse = objectMapper.readValue(value, QuestionResponse.class);

        assertAll(
            () -> assertThat(questionResponse.getTitle()).isEqualTo(TEST_QUESTION_TITLE),
            () -> assertThat(questionResponse.getContent()).isEqualTo(TEST_QUESTION_CONTENT)
        );
    }

    @DisplayName("질문 수정")
    @Test
    void update() throws Exception {
        QuestionUpdateRequest request = QuestionUpdateRequest.builder()
            .title("Changed Title")
            .content("Changed Content")
            .build();
        String inputJson = objectMapper.writeValueAsString(request);

        willDoNothing().given(questionService).update(anyLong(), anyLong(), any(QuestionUpdateRequest.class));

        patchAction("/api/questions/1", inputJson, TEST_TOKEN)
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

    @DisplayName("질문 검색 - 제목에 포함된 키워드")
    @Test
    void search() throws Exception {
        Question question1 = Question.builder()
            .id(1L)
            .userId(1L)
            .title(QuestionTitle.from("스택과 큐의 차이"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .build();

        Question question2 = Question.builder()
            .id(2L)
            .userId(1L)
            .title(QuestionTitle.from("오버스택플로우"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .build();

        QuestionResponses responses = QuestionResponses.from(Lists.newArrayList(question1, question2));

        given(questionService.searchByTitle(anyString())).willReturn(responses);

        MvcResult mvcResult = getAction("/api/questions?keyword=스택")
            .andExpect(status().isOk())
            .andReturn();
        String value = mvcResult.getResponse().getContentAsString();
        QuestionResponses questionResponses = objectMapper.readValue(value, QuestionResponses.class);

        assertAll(
            () -> assertThat(questionResponses.getQuestions().get(0).getTitle()).isEqualTo("스택과 큐의 차이"),
            () -> assertThat(questionResponses.getQuestions().get(1).getTitle()).isEqualTo("오버스택플로우")
        );
    }

    @DisplayName("질문에 달린 태그 생성 및 교체")
    @Test
    void hashtagsOnQuestion() throws Exception {
        Question question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(QuestionTitle.from(TEST_QUESTION_TITLE))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .build();
        HashtagsRequest hashtagsRequest = HashtagsRequest.builder()
            .hashtags(Sets.newSet("java", "network"))
            .build();
        String inputJson = objectMapper.writeValueAsString(hashtagsRequest);

        willDoNothing().given(questionService).saveOrUpdateHashtags(anyLong(), any());

        putAction(String.format("/api/questions/%d/hashtags", question.getId()), inputJson, TEST_TOKEN)
            .andExpect(status().isNoContent());

        verify(questionService).saveOrUpdateHashtags(eq(1L), any(HashtagsRequest.class));
    }

    @DisplayName("질문에 달린 해시태그 삭제")
    @Test
    void deleteHashtagOnQuestion() throws Exception {
        deleteAction("/api/questions/1/hashtags/2", TEST_TOKEN)
            .andExpect(status().isNoContent());

        verify(questionService).deleteHashtag(eq(1L), eq(2L));
    }
}
