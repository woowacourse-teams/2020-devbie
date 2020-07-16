package underdogs.devbie.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.dto.UserInfoResponse;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.domain.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveOrUpdateUser(UserInfoResponse userInfoResponse) {
        User user = userRepository.findByOauthId(userInfoResponse.getId())
            .map(u -> u.updateOAuthInfo(userInfoResponse))
            .orElse(userInfoResponse.toEntity());

        userRepository.save(user);

        return user;
    }
}
