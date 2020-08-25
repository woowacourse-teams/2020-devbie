package underdogs.devbie.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionFavoriteRepository
    extends JpaRepository<QuestionFavorite, Long>, FavoriteRepository<QuestionFavorite> {

    @Override
    @Query("select q.questionId from QuestionFavorite  q where q.userId = :userId")
    List<Long> findAllByUserId(@Param("userId") Long userId);

    @Override
    @Query("select q from QuestionFavorite q where q.questionId = :questionId and q.userId = :userId")
    Optional<QuestionFavorite> findByObjectAndUserId(@Param("questionId") Long questionId,
        @Param("userId") Long userId);
}
