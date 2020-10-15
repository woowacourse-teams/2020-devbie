package underdogs.devbie.user.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import type.RoleType;
import underdogs.devbie.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.user.dto.UserInfoDto;

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

    private String image;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    public User updateOauthInfo(UserInfoDto userInfoDto) {
        validateUserInfo(userInfoDto);
        this.email = userInfoDto.getEmail();
        return this;
    }

    public void updateUserInfo(User user) {
        this.name = user.name;
        this.email = user.email;
    }

    public void updateUserImage(String image) {
        this.image = image;
    }

    private void validateUserInfo(UserInfoDto userInfoDto) {
        if (!userInfoDto.getId().equals(this.oauthId)) {
            throw new IllegalArgumentException("일치하지않은 유저입니다. "
                + "userInfoResponse=" + userInfoDto
                + " User =" + this);
        }
    }

    public boolean isNotAdmin() {
        return this.roleType != RoleType.ADMIN;
    }
}
