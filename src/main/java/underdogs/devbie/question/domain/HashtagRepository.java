package underdogs.devbie.question.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>  {

    @Query("select h from Hashtag h where h.tagName.name = :tagName")
    Optional<Hashtag> findByTagName(String tagName);
}
