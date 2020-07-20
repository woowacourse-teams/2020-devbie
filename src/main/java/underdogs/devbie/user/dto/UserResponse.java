package underdogs.devbie.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import underdogs.devbie.user.domain.User;

@Getter
@Builder
@RequiredArgsConstructor
public class UserResponse {
    private final String email;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .email(user.getEmail())
            .build();
    }
}
