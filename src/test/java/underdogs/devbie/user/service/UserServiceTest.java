package underdogs.devbie.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import underdogs.devbie.auth.dto.UserInfoResponse;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;

@SpringBootTest
class UserServiceTest {

	public static final String TEST_USER_EMAIL = "underdogs@debvie.link";
	public static final String TEST_OAUTH_ID = "TEST_OAUTH_ID";

	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		userService = new UserService(userRepository);
	}

	@DisplayName("Oauth로부터 User 모델 신규 저장 및 업데이트")
	@Test
	void saveOrUpdateOauthUser() {
	    // given
		UserInfoResponse userInfoResponse = new UserInfoResponse(TEST_OAUTH_ID, TEST_USER_EMAIL);
		User mockUser = User.builder()
			.id(1L)
			.oauthId(TEST_OAUTH_ID)
			.email(TEST_USER_EMAIL)
			.build();
		given(userRepository.findByOauthId(any())).willReturn(Optional.of(mockUser));

	    // when
		User user = userService.saveOrUpdateUser(userInfoResponse);

		// then
		assertThat(user.getId()).isEqualTo(1L);
		assertThat(user.getOauthId()).isEqualTo(TEST_OAUTH_ID);
		assertThat(user.getEmail()).isEqualTo(TEST_USER_EMAIL);
	}
}