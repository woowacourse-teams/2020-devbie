package underdogs.devbie.question.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

    Page<Question> findAllBy(Pageable pageable);
}
