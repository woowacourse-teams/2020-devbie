package underdogs.devbie.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.HashtagRepository;
import underdogs.devbie.question.dto.HashtagCreateRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public Long save(HashtagCreateRequest request) {
        Hashtag savedHashtag = hashtagRepository.save(request.toEntity());
        return savedHashtag.getId();
    }
}
