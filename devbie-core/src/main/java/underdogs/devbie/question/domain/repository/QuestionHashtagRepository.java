package underdogs.devbie.question.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import underdogs.devbie.question.domain.QuestionHashtag;

public interface QuestionHashtagRepository
    extends JpaRepository<QuestionHashtag, Long>, QuestionHashtagRepositoryCustom {

    Optional<QuestionHashtag> findByQuestionIdAndHashtagId(Long questionId, Long hashtagId);

    List<QuestionHashtag> findAllByQuestionId(Long questionId);
}
