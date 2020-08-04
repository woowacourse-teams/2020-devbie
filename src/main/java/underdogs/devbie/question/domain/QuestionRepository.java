package underdogs.devbie.question.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q where q.title.title like %:keyword%")
    List<Question> findByTitleLike(@Param("keyword") String keyword);
}
