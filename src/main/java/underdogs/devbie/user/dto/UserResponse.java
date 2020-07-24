package underdogs.devbie.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import underdogs.devbie.user.domain.User;

@AllArgsConstructor
@Getter
@Builder
public class UserResponse {

    private String email;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .email(user.getEmail())
            .build();
    }
}
