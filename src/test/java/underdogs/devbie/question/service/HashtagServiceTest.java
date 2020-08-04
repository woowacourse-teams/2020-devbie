package underdogs.devbie.question.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.question.domain.TagNameTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.HashtagRepository;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.dto.HashtagCreateRequest;

@ExtendWith(MockitoExtension.class)
class HashtagServiceTest {

    private HashtagService hashtagService;

    @Mock
    private HashtagRepository hashtagRepository;

    @BeforeEach
    void setUp() {
        hashtagService = new HashtagService(hashtagRepository);
    }

    @DisplayName("해시태그 생성")
    @Test
    void save() {
        HashtagCreateRequest request = HashtagCreateRequest.builder()
            .tagName(TEST_HASHTAG_NAME)
            .build();
        Hashtag hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();
        given(hashtagRepository.save(any(Hashtag.class))).willReturn(hashtag);

        Long hashtagId = hashtagService.save(request);

        assertThat(hashtagId).isEqualTo(hashtag.getId());
    }

}