package underdogs.devbie.question.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionHashtagRepository extends JpaRepository<QuestionHashtag, Long> {

    void deleteByQuestionIdAndHashtagId(Long questionId, Long hashtagId);

    Optional<QuestionHashtag> findByQuestionIdAndHashtagId(Long questionId, Long hashtagId);

    List<QuestionHashtag> findAllByQuestionId(Long questionId);

    @Modifying
    @Query("delete from QuestionHashtag q where q.hashtag.id in :ids")
    void deleteAllByHashtagIds(List<Long> ids);
}
