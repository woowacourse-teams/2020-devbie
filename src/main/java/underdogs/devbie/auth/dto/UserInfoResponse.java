package underdogs.devbie.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserInfoResponse {

    private String id;
    private String email;

    public User toEntity() {
        return User.builder()
            .oauthId(id)
            .email(email)
            .build();
    }
}
