package underdogs.devbie.favorite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import underdogs.devbie.exception.BadRequestException;
import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.favorite.domain.Favorite;
import underdogs.devbie.favorite.domain.NoticeFavorite;
import underdogs.devbie.favorite.domain.NoticeFavoriteRepository;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.service.NoticeService;
import underdogs.devbie.user.service.UserService;

@Service
public class NoticeFavoriteService extends FavoriteService {

    private NoticeService noticeService;

    public NoticeFavoriteService(NoticeFavoriteRepository noticeFavoriteRepository, UserService userService,
        NoticeService noticeService) {
        this.favoriteRepository = noticeFavoriteRepository;
        this.userService = userService;
        this.noticeService = noticeService;
    }

    @Override
    public NoticeResponses findFavorites(Long userId) {
        List<Long> noticeIds = favoriteRepository.findAllByUserId(userId);
        return noticeService.findAllByIds(noticeIds);
    }

    @Override
    public void createFavorite(Long objectId, Long userId) {
        Optional<Favorite> optionalFavorite = favoriteRepository.findByObjectAndUserId(objectId, userId);
        if (optionalFavorite.isPresent()) {
            throw new BadRequestException("이미 즐겨찾기 추가된 항목입니다.");
        }
        favoriteRepository.save(NoticeFavorite.of(objectId, userId));
    }

    @Override
    public void deleteFavorite(Long objectId, Long userId) {
        Optional<Favorite> optionalFavorite = favoriteRepository.findByObjectAndUserId(objectId, userId);
        Favorite favorite = optionalFavorite.orElseThrow(() -> new NotExistException("Favorite"));

        favoriteRepository.delete(favorite);

    }
}