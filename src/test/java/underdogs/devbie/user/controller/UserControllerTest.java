package underdogs.devbie.user.controller;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.aws.S3Service;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;
import underdogs.devbie.user.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest extends MvcTest {

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @MockBean
    private UserService userService;

    @MockBean
    private S3Service s3Service;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    @DisplayName("유저 정보 조회")
    @Test
    void findUser() throws Exception {
        getAction("/api/users", TEST_TOKEN)
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(TEST_USER_EMAIL)));
    }

    @DisplayName("유저 생성 - OAuthId 없이")
    @Test
    void saveUserWithoutOAuthId() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
            .name("bsdg")
            .email("atdd@atdd.com")
            .build();
        String inputJson = objectMapper.writeValueAsString(request);

        given(userService.saveWithoutOAuthId(any(UserCreateRequest.class))).willReturn(1L);

        postAction("/api/users", inputJson)
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("location", "/api/users/" + 1L))
            .andDo(print());
    }
}
