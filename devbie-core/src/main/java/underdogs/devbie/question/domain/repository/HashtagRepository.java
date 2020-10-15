package underdogs.devbie.question.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.TagName;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>  {

    Optional<Hashtag> findByTagName(TagName tagName);
}
