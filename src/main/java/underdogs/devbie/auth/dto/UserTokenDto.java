package underdogs.devbie.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.user.domain.RoleType;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserTokenDto {

    private Long id;

    private RoleType roleType;

    public static UserTokenDto from(User user) {
        return UserTokenDto.builder()
            .id(user.getId())
            .roleType(user.getRoleType())
            .build();
    }
}
