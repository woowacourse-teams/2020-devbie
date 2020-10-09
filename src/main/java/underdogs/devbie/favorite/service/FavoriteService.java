package underdogs.devbie.favorite.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exception.NotExistException;
import underdogs.devbie.favorite.domain.Favorite;
import underdogs.devbie.favorite.domain.FavoriteRepository;
import underdogs.devbie.user.service.UserService;

@Service
@Transactional(readOnly = true)
public abstract class FavoriteService<T extends Favorite> {

    protected FavoriteRepository favoriteRepository;
    protected UserService userService;

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
