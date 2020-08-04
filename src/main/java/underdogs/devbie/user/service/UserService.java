package underdogs.devbie.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.dto.UserInfoDto;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;
import underdogs.devbie.user.dto.UserCreateRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveOrUpdateUser(UserInfoDto userInfoDto) {
        User user = userRepository.findByOauthId(userInfoDto.getId())
            .map(u -> u.updateOauthInfo(userInfoDto))
            .orElse(userInfoDto.toEntity());

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

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
