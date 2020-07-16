package underdogs.devbie.auth.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInfoResponse {

    private String id;
    private String login;
    private String email;

    public User toEntity() {
        return User.builder()
            .oauthId(id)
            .email(email)
            .build();
    }
}
