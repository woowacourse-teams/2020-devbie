package underdogs.devbie.auth.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.auth.service.AuthService;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends MvcTest {

    @MockBean
    private AuthService authService;
    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;
    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @Test
    void fetchLoginUrl() throws Exception {
        given(authService.fetchLoginUrl()).willReturn("fetch-login-url");

        getAction("/api/oauth/login-url", "", "")
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("fetch-login-url")))
                .andDo(print());
    }

    @Test
    void login() throws Exception{

        postAction("/api/oauth/login?code=1234", "", "")
                .andExpect(status().isOk())
                .andDo(print());
    }
}