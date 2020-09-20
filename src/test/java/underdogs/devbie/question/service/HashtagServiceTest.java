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
import underdogs.devbie.question.dto.HashtagUpdateRequest;
import underdogs.devbie.question.exception.HashtagNotExistedException;

@ExtendWith(MockitoExtension.class)
class HashtagServiceTest {

    private HashtagService hashtagService;

    @Mock
    private HashtagRepository hashtagRepository;

    private Hashtag hashtag;

    @BeforeEach
    void setUp() {
        hashtagService = new HashtagService(hashtagRepository);

        hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();
    }

    @DisplayName("해시태그 생성")
    @Test
    void save() {
        HashtagCreateRequest request = HashtagCreateRequest.builder()
            .tagName(TEST_HASHTAG_NAME)
            .build();
        given(hashtagRepository.save(any(Hashtag.class))).willReturn(hashtag);

        Long hashtagId = hashtagService.save(request);

        assertThat(hashtagId).isEqualTo(hashtag.getId());
    }

    @DisplayName("해시태그 목록 조회")
    @Test
    void readAll() {
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
        given(hashtagRepository.findById(eq(100L))).willReturn(Optional.of(hashtag));

        HashtagResponse response = hashtagService.read(hashtag.getId());

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(hashtag.getId()),
            () -> assertThat(response.getTagName()).isEqualTo(hashtag.getTagName().getName())
        );
    }

    @DisplayName("존재하지 않는 해시태그 예외처리")
    @Test
    void read_When_Invalid_Hashtag_Should_Throw_HashtagNotExistedException() {

        given(hashtagRepository.findById(anyLong())).willThrow(new HashtagNotExistedException());

        assertThatThrownBy(() -> hashtagService.read(1L))
            .isInstanceOf(HashtagNotExistedException.class)
            .hasMessage("존재하지 않는 해시태그입니다.");

    }

    @DisplayName("이름으로 해시태그 조회")
    @Test
    void readByTagName() {
        given(hashtagRepository.findByTagName(anyString())).willReturn(Optional.of(hashtag));

        HashtagResponse response = hashtagService.readByTagName(hashtag.getTagName().getName());

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(hashtag.getId()),
            () -> assertThat(response.getTagName()).isEqualTo(hashtag.getTagName().getName())
        );
    }

    @DisplayName("해시태그 수정")
    @Test
    void update() {
        HashtagUpdateRequest request = HashtagUpdateRequest.builder()
            .tagName("changed name")
            .build();
        given(hashtagRepository.findById(anyLong())).willReturn(Optional.of(hashtag));

        hashtagService.update(hashtag.getId(), request);

        HashtagResponse response = hashtagService.read(hashtag.getId());

        assertThat(response.getTagName()).isEqualTo(request.getTagName());
    }

    @DisplayName("해시태그 삭제")
    @Test
    void delete() {
        willDoNothing().given(hashtagRepository).deleteById(anyLong());

        hashtagService.delete(hashtag.getId());

        verify(hashtagRepository).deleteById(eq(100L));
    }
}
