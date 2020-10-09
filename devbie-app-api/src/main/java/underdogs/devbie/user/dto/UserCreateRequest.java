package underdogs.devbie.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class UserCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    public User toEntity() {
        return User.builder()
            .name(name)
            .email(email)
            .build();
    }
}
