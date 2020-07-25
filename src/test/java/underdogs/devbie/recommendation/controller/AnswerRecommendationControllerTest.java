package underdogs.devbie.recommendation.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.recommendation.RecommendationAcceptanceTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.recommendation.service.AnswerRecommendationService;
import underdogs.devbie.user.domain.User;

@WebMvcTest(AnswerRecommendationController.class)
class AnswerRecommendationControllerTest extends MvcTest {

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @MockBean
    private AnswerRecommendationService answerRecommendationService;

    @Test
    void count() throws Exception{
        getAction("/api/recommendation/answer/1")
            .andExpect(status().isOk());
    }

    @Test
    void createRecommendation() throws Exception {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);

        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, RECOMMENDATION);

        postAction("/api/recommendation/answer/1", inputJson, "")
            .andExpect(status().isCreated());
    }

    @Test
    void toggleRecommendation() throws Exception {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);

        String inputJson = String.format(RECOMMENDATION_TYPE_FORMAT, RECOMMENDATION);

        patchAction("/api/recommendation/answer/1", inputJson, "")
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecommendation() throws Exception {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);

        deleteAction("/api/recommendation/answer/1", "", "")
            .andExpect(status().isNoContent());
    }
}