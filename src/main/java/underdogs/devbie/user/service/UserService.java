package underdogs.devbie.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.dto.UserInfoResponse;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;
import underdogs.devbie.user.dto.UserCreateRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveOrUpdateUser(UserInfoResponse userInfoResponse) {
        User user = userRepository.findByOauthId(userInfoResponse.getId())
            .map(u -> u.updateOauthInfo(userInfoResponse))
            .orElse(userInfoResponse.toEntity());

        userRepository.save(user);

        return user;
    }

    public User findById(long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 유저입니다. id=" + userId));
    }

    @Transactional
    public Long saveWithoutOAuthId(UserCreateRequest request) {
        User savedUser = userRepository.save(request.toEntity());
        return savedUser.getId();
    }
}
