package underdogs.devbie.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserTokenDto {

    private Long id;

    public static UserTokenDto from(User user) {
        return new UserTokenDto(user.getId());
    }
}
