package underdogs.devbie.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import underdogs.devbie.user.dto.UserCreateRequest;
import underdogs.devbie.user.dto.UserUpdateImageRequest;
import underdogs.devbie.user.dto.UserUpdateInfoRequest;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;
import underdogs.devbie.user.dto.UserInfoDto;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
    }

    @DisplayName("Oauth로부터 User 모델 신규 저장 및 업데이트")
    @Test
    void saveOrUpdateOauthUser() {
        UserInfoDto userInfoDto = new UserInfoDto(TEST_OAUTH_ID, TEST_USER_EMAIL, TEST_GITHUB_ID, TEST_AVATAR_URL);
        given(userRepository.findByOauthId(any())).willReturn(Optional.of(user));

        User savedUser = userService.saveOrUpdateUser(userInfoDto);

        assertAll(
            () -> assertThat(savedUser.getId()).isEqualTo(1L),
            () -> assertThat(savedUser.getOauthId()).isEqualTo(TEST_OAUTH_ID),
            () -> assertThat(savedUser.getEmail()).isEqualTo(TEST_USER_EMAIL)
        );
    }

    @DisplayName("Id로 유저 조회")
    @Test
    void findById() {
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        User findUser = userService.findById(1L);

        assertAll(
            () -> assertThat(findUser.getId()).isEqualTo(1L),
            () -> assertThat(findUser.getOauthId()).isEqualTo(TEST_OAUTH_ID),
            () -> assertThat(findUser.getEmail()).isEqualTo(TEST_USER_EMAIL)
        );
    }

    @DisplayName("유저 생성 - OAuthId 없이")
    @Test
    void saveUserWithoutOAuthId() {
        UserCreateRequest request = UserCreateRequest.builder()
            .email("atdd@atdd.com")
            .build();
        given(userRepository.save(any(User.class))).willReturn(user);

        Long userId = userService.saveWithoutOAuthId(request);

        assertThat(userId).isEqualTo(user.getId());
    }

    @DisplayName("유저 정보 수정")
    @Test
    void updateUserInfo() {
        UserUpdateInfoRequest request = UserUpdateInfoRequest.builder()
            .name("underdogs")
            .email("underdogs@devbie.com")
            .build();
        userService.updateUserInfo(user, request);
    }

    @DisplayName("유저 프로필 수정")
    @Test
    void updateUserImage() throws IOException {
        ClassPathResource fileResource = new ClassPathResource(
            "/devbie.png");

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "image", fileResource.getFilename(),
            MediaType.MULTIPART_FORM_DATA_VALUE,
            fileResource.getInputStream());

        UserUpdateImageRequest.builder()
            .image(mockMultipartFile)
            .build();

        userService.updateUserImage(user, "imagePath");
    }

    @DisplayName("유저 삭제")
    @Test
    void deleteById() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteById(1L);
        verify(userRepository).deleteById(eq(1L));
    }
}
