package underdogs.devbie.question.domain.repository;

import java.util.List;

import underdogs.devbie.question.domain.Hashtag;

public interface HashtagRepositoryCustom {

    void deleteEmptyRefHashtag(List<Hashtag> hashtags);
}
