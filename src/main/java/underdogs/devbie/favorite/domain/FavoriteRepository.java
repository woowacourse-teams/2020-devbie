package underdogs.devbie.favorite.domain;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository<T extends Favorite> {

    Optional<T> findByObjectAndUserId(Long objectId, Long userId);

    List<Long> findAllByUserId(Long userId);

    T save(T t);

    void delete(T t);
}
