package underdogs.devbie.question.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionHashtagRepository extends JpaRepository<QuestionHashtag, Long> {

    Optional<QuestionHashtag> findByQuestionIdAndHashtagId(Long questionId, Long hashtagId);

    List<QuestionHashtag> findAllByQuestionId(Long questionId);

    @Modifying
    @Query("delete from QuestionHashtag q where q.hashtag.id in :ids")
    void deleteAllByHashtagIds(@Param("ids") List<Long> ids);

    @Query("select q.question.id from QuestionHashtag q where q.hashtag.id = :hashtagId")
    List<Long> findQuestionIdsByHashtagId(@Param("hashtagId") Long hashtagId);
}
