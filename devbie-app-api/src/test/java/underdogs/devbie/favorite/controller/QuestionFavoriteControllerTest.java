package underdogs.devbie.favorite.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.user.controller.UserControllerTest.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.resolver.LoginUserArgumentResolver;
import underdogs.devbie.favorite.QuestionFavoriteController;
import underdogs.devbie.favorite.service.QuestionFavoriteService;
import underdogs.devbie.user.domain.User;

@WebMvcTest(QuestionFavoriteController.class)
class QuestionFavoriteControllerTest extends MvcTest {

    private static String URL = "/api/favorite-question?objectType=question";

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @MockBean
    private QuestionFavoriteService questionFavoriteService;

    @DisplayName("질문 즐겨 찾기 추가")
    @Test
    void createFavorite() throws Exception {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);

        postAction(URL + "&objectId=" + 1L).andExpect(status().isCreated());
    }

    @DisplayName("질문 즐겨 찾기 삭제")
    @Test
    public void deleteFavorite() throws Exception {
        User user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);

        deleteAction(URL + "&objectId=" + 1L, "")
            .andExpect(status().isNoContent());
    }

}
