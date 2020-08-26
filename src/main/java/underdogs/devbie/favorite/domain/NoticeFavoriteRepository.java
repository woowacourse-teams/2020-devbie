package underdogs.devbie.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeFavoriteRepository extends
    JpaRepository<NoticeFavorite, Long>,
    FavoriteRepository<NoticeFavorite> {

    @Override
    @Query("select n.noticeId from NoticeFavorite n where n.userId = :userId")
    List<Long> findAllByUserId(@Param("userId") Long userId);

    @Override
    @Query("select n from NoticeFavorite n where n.noticeId = :noticeId and n.userId = :userId")
    Optional<NoticeFavorite> findByObjectAndUserId(@Param("noticeId") Long noticeId, @Param("userId") Long userId);
}
