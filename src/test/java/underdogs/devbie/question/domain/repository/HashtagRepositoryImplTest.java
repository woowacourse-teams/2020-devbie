package underdogs.devbie.question.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.TagName;

@SpringBootTest
@Transactional
class HashtagRepositoryImplTest {

    @Autowired
    private HashtagRepository hashtagRepository;

    @BeforeEach
    public void setUp() {
        Hashtag hashtag1 = Hashtag.builder().id(10L).tagName(TagName.from("java")).build();
        Hashtag hashtag2 = Hashtag.builder().id(11L).tagName(TagName.from("network")).build();

        hashtagRepository.save(hashtag1);
        hashtagRepository.save(hashtag2);
    }

    @DisplayName("참조하고 있는 질문이 없을 경우 해시태그 자동 삭제")
    @Test
    public void deleteEmptyRefHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();

        hashtagRepository.deleteEmptyRefHashtag(hashtags);

        List<Hashtag> afterDeletedHashtags = hashtagRepository.findAll();

        assertThat(afterDeletedHashtags).isEmpty();
    }

}