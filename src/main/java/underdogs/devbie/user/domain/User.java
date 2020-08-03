package underdogs.devbie.user.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.auth.dto.UserInfoResponse;
import underdogs.devbie.config.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    public User updateOauthInfo(UserInfoResponse userInfoResponse) {
        validateUserInfo(userInfoResponse);
        this.email = userInfoResponse.getEmail();
        return this;
    }

    private void validateUserInfo(UserInfoResponse userInfoResponse) {
        if (!userInfoResponse.getId().equals(this.oauthId)) {
            throw new IllegalArgumentException("일치하지않은 유저입니다. "
                + "userInfoResponse=" + userInfoResponse
                + " User =" + this);
        }
    }
}
