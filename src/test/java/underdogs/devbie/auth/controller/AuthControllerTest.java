package underdogs.devbie.auth.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.auth.dto.JwtTokenResponse;
import underdogs.devbie.auth.service.AuthService;

@DisplayName("Auth컨트롤러")
@WebMvcTest(AuthController.class)
class AuthControllerTest extends MvcTest {

    private static final String TEST_LOGIN_URL = "login-url";
    private static final String TEST_TOKEN = "testToken";
    private static final String TEST_CODE = "1234";

    @MockBean
    private AuthService authService;
    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;
    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @DisplayName("fetch 로그인 url")
    @Test
    void fetchLoginUrl() throws Exception {
        given(authService.fetchLoginUrl()).willReturn(TEST_LOGIN_URL);

        getAction("/api/oauth/login-url", "", "")
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_LOGIN_URL)))
                .andDo(print());
    }

    @DisplayName("로그인")
    @Test
    void login() throws Exception{
        given(authService.createToken(TEST_CODE)).willReturn(JwtTokenResponse.from(TEST_TOKEN));

        String url = "/api/oauth/login" + "?code=" + TEST_CODE;

        postAction(url, "", "")
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_TOKEN)))
                .andDo(print());
    }
}