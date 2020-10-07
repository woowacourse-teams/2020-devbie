package underdogs.devbie.question.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import underdogs.devbie.question.domain.Question;

public interface QuestionRepositoryCustom {

    Page<Question> findAllBy(Pageable pageable);
}
