package underdogs.devbie.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import underdogs.devbie.user.domain.RoleType;
import underdogs.devbie.user.domain.User;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class UserResponse {

    private Long id;
    private String email;
    private String image;
    private String name;
    private RoleType roleType;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .image(user.getImage())
            .roleType(user.getRoleType())
            .build();
    }
}
