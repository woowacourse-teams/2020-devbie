package underdogs.devbie.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.user.domain.RoleType;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserTokenDto {

    private Long id;

    private RoleType roleType;

    public static UserTokenDto from(User user) {
        return new UserTokenDto(user.getId(), user.getRoleType());
    }
}
