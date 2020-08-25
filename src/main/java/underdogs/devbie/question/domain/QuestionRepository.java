package underdogs.devbie.question.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {

    @Query("select q from Question q where lower(q.title.title) like lower(concat('%', :title, '%')) "
        + "or lower(q.content.content) like lower(concat('%', :content, '%'))")
    List<Question> findByBothLike(String title, String content);
}
