package underdogs.devbie.question.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionHashtagRepository extends JpaRepository<QuestionHashtag, Long> {

    void deleteByQuestionIdAndHashtagId(Long questionId, Long hashtagId);

    Optional<QuestionHashtag> findByQuestionIdAndHashtagId(Long questionId, Long hashtagId);
}
