package underdogs.devbie.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import underdogs.devbie.user.domain.User;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .build();
    }
}
