package underdogs.devbie.question.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.HashtagRepository;
import underdogs.devbie.question.dto.HashtagCreateRequest;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.HashtagResponses;
import underdogs.devbie.question.exception.HashtagNotExistedException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public Long save(HashtagCreateRequest request) {
        Hashtag savedHashtag = hashtagRepository.save(request.toEntity());
        return savedHashtag.getId();
    }

    public HashtagResponses readAll() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        return HashtagResponses.from(hashtags);
    }

    public HashtagResponse read(Long id) {
        Hashtag hashtag = readOne(id);
        return HashtagResponse.from(hashtag);
    }

    private Hashtag readOne(Long hashtagId) {
        return hashtagRepository.findById(hashtagId)
            .orElseThrow(HashtagNotExistedException::new);
    }
}
