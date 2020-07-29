package underdogs.devbie.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import underdogs.devbie.user.domain.User;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class UserResponse {

    private String email;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .email(user.getEmail())
            .build();
    }
}
