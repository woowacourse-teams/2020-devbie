package underdogs.devbie.favorite.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.favorite.domain.Favorite;
import underdogs.devbie.favorite.domain.FavoriteRepository;

@Service
@Transactional(readOnly = true)
public abstract class FavoriteService<T extends Favorite> {

    protected FavoriteRepository favoriteRepository;

    public abstract Object findFavorites(Long userId);

    @Transactional
    public abstract void createFavorite(Long objectId, Long userId);

    @Transactional
    public void deleteFavorite(Long objectId, Long userId) {
        Optional<Favorite> optionalFavorite = favoriteRepository.findByObjectAndUserId(objectId, userId);
        Favorite favorite = optionalFavorite.orElseThrow(() -> new NotExistException("Favorite"));

        favoriteRepository.delete(favorite);
    }
}
