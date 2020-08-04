package underdogs.devbie.question.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionHashtagRepository extends JpaRepository<QuestionHashtag, Long> {

    @Query("select qh from QuestionHashtag qh " +
        "where qh.question.id = :questionId and qh.hashtag.id = :hashtagId")
    Optional<QuestionHashtag> findByQuestionIdAndByHashtagId(Long questionId, Long hashtagId);
}
