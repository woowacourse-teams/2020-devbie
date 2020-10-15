package underdogs.devbie.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import type.RoleType;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class UserInfoDto {

    private String id;
    private String email;
    private String login;
    private String avatar_url;

    public User toEntity() {
        return User.builder()
            .oauthId(id)
            .email(email)
            .name(login)
            .image(avatar_url)
            .roleType(RoleType.USER)
            .build();
    }
}
