package underdogs.devbie.question.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.question.domain.TagNameTest.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
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
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.HashtagResponses;

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

    @DisplayName("해시태그 목록 조회")
    @Test
    void readAll() {
        Hashtag hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();
        List<Hashtag> hashtags = Lists.newArrayList(hashtag);

        given(hashtagRepository.findAll()).willReturn(hashtags);

        HashtagResponses hashtagResponses = hashtagService.readAll();

        assertAll(
            () -> assertThat(hashtagResponses.getHashtags().get(0).getId()).isEqualTo(100L),
            () -> assertThat(hashtagResponses.getHashtags().get(0).getTagName()).isEqualTo(TEST_HASHTAG_NAME)
        );
    }

    @DisplayName("해시태그 단건 조회")
    @Test
    void read() {
        Hashtag hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();

        given(hashtagRepository.findById(eq(100L))).willReturn(Optional.of(hashtag));

        HashtagResponse response = hashtagService.read(hashtag.getId());

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(hashtag.getId()),
            () -> assertThat(response.getTagName()).isEqualTo(hashtag.getTagName().getName())
        );
    }

    @DisplayName("이름으로 해시태그 조회")
    @Test
    void readByTagName() {
        Hashtag hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();

        given(hashtagRepository.findByTagName(anyString())).willReturn(Optional.of(hashtag));

        HashtagResponse response = hashtagService.readByTagName(hashtag.getTagName().getName());

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(hashtag.getId()),
            () -> assertThat(response.getTagName()).isEqualTo(hashtag.getTagName().getName())
        );
    }
}