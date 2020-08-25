package underdogs.devbie.favorite.domain;

import java.util.Objects;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class NoticeFavorite extends Favorite {

    private Long noticeId;

    private NoticeFavorite(Long userId, Long noticeId) {
        super(userId);
        this.noticeId = noticeId;
    }

    public static NoticeFavorite of(Long noticeId, Long userId) {
        validateParameters(noticeId, userId);
        return new NoticeFavorite(userId, noticeId);
    }

    private static void validateParameters(Long noticeId, Long userId) {
        if (Objects.isNull(noticeId) || Objects.isNull(userId)) {
            throw new CreateFailException();
        }
    }

}
