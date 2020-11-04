package underdogs.devbie.user.controller;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.resolver.LoginUserArgumentResolver;
import underdogs.devbie.s3.S3Service;
import underdogs.devbie.user.UserController;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.dto.UserCreateRequest;
import underdogs.devbie.user.dto.UserUpdateInfoRequest;
import underdogs.devbie.user.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest extends MvcTest {

    public static final String TEST_USER_EMAIL = "underdogs@devbie.link";
    public static final String TEST_OAUTH_ID = "TEST_OAUTH_ID";

    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;
    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;
    @MockBean
    private UserService userService;
    @MockBean
    private S3Service s3Service;

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

    @DisplayName("유저 정보 수정")
    @Test
    void updateUser() throws Exception {
        UserUpdateInfoRequest request = UserUpdateInfoRequest.builder()
            .name("underdogs")
            .email("underdogs@devbie.com")
            .build();
        String inputJson = objectMapper.writeValueAsString(request);

        patchAction("/api/users/me", inputJson)
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("유저 프로필 이미지 수정")
    @Test
    void updateUserImage() throws Exception {
        ClassPathResource fileResource = new ClassPathResource(
            "/devbie.png");

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "image", fileResource.getFilename(),
            MediaType.MULTIPART_FORM_DATA_VALUE,
            fileResource.getInputStream());

        patchAction("/api/users/me/image", mockMultipartFile)
            .andExpect(status().isNoContent())
            .andDo(print());

    }
}
