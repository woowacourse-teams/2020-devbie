package underdogs.devbie.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.auth.service.dto.UserInfoResponse;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String email;

    public User updateOAuthInfo(UserInfoResponse userInfoResponse) {
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
