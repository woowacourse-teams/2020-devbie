package underdogs.devbie.favorite.domain;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.common.BaseTimeEntity;
import underdogs.devbie.exception.CreateFailException;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Favorite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Long userId;

    public Favorite(Long userId) {
        validateParameters(userId);
        this.userId = userId;
    }

    private void validateParameters(Long userId) {
        if (Objects.isNull(userId)) {
            throw new CreateFailException();
        }
    }

}
