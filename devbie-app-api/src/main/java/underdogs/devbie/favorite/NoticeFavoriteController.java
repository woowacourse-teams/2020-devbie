package underdogs.devbie.favorite;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.favorite.service.NoticeFavoriteService;

@RestController
@RequestMapping("/api/favorite-notice")
public class NoticeFavoriteController extends FavoriteController {

    public NoticeFavoriteController(NoticeFavoriteService noticeFavoriteService) {
        this.favoriteService = noticeFavoriteService;
    }
}
