package underdogs.devbie.question.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.HashtagRepository;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.dto.HashtagCreateRequest;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.HashtagResponses;
import underdogs.devbie.question.dto.HashtagUpdateRequest;
import underdogs.devbie.question.exception.HashtagNotExistedException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public Long save(HashtagCreateRequest request) {
        Hashtag hashtag = hashtagRepository.findByTagName(request.getTagName())
            .orElse(request.toEntity());

        Hashtag savedHashtag = hashtagRepository.save(hashtag);
        return savedHashtag.getId();
    }

    public HashtagResponses readAll() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        return HashtagResponses.from(hashtags);
    }

    @Cacheable(value = "HashtagResponse")
    public HashtagResponse read(Long id) {
        Hashtag hashtag = readOne(id);
        return HashtagResponse.from(hashtag);
    }

    private Hashtag readOne(Long hashtagId) {
        return hashtagRepository.findById(hashtagId)
            .orElseThrow(HashtagNotExistedException::new);
    }

    public HashtagResponse readByTagName(String tagName) {
        Hashtag hashtag = hashtagRepository.findByTagName(tagName)
            .orElseThrow(HashtagNotExistedException::new);
        return HashtagResponse.from(hashtag);
    }

    @Transactional
    @CachePut(value = "HashtagResponse", key = "#id")
    public HashtagResponse update(Long id, HashtagUpdateRequest request) {
        Hashtag hashtag = readOne(id);
        hashtag.update(request.toEntity());
        return HashtagResponse.from(hashtag);
    }

    @Transactional
    @CacheEvict(value = "HashtagResponse", key = "#id")
    public void delete(Long id) {
        hashtagRepository.deleteById(id);
    }

    @Transactional
    public Hashtag findOrCreateHashtag(String tagName) {
        Hashtag hashtag = hashtagRepository.findByTagName(tagName)
            .orElse(Hashtag.builder()
                .tagName(TagName.from(tagName))
                .build());
        return hashtagRepository.save(hashtag);
    }
}
