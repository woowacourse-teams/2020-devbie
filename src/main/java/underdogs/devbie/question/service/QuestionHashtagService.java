package underdogs.devbie.question.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.HashtagRepository;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtagRepository;
import underdogs.devbie.question.domain.QuestionHashtags;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.exception.HashtagNotExistedException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionHashtagService {

    private final HashtagRepository hashtagRepository;
    private final QuestionHashtagRepository questionHashtagRepository;

    @Transactional
    public void saveHashtags(Question question, Set<String> hashtags) {
        QuestionHashtags questionHashtags = mapToQuestionHashtags(question, hashtags);
        question.setHashtags(questionHashtags);
    }

    @Transactional
    public void updateHashtags(Question question, Set<String> hashtags) {
        QuestionHashtags questionHashtags = mapToQuestionHashtags(question, hashtags);
        question.setHashtags(questionHashtags);

        List<Long> deleteTargetIds = questionHashtags.findDeleteTargetIds(
            questionHashtagRepository.findAllByQuestionId(question.getId()));
        questionHashtagRepository.deleteAllByHashtagIds(deleteTargetIds);
    }

    private QuestionHashtags mapToQuestionHashtags(Question question, Set<String> hashtags) {
        return new QuestionHashtags(hashtags
            .stream()
            .map(tagName -> {
                Hashtag hashtag = findOrCreateHashtag(tagName);
                hashtagRepository.save(hashtag);
                QuestionHashtag questionHashtag = findOrCreateQuestionHashtag(question, hashtag);
                questionHashtagRepository.save(questionHashtag);
                return questionHashtag;
            })
            .collect(Collectors.toSet()));
    }

    private Hashtag findOrCreateHashtag(String tagName) {
        return hashtagRepository.findByTagName(tagName)
            .orElse(Hashtag.builder()
                .tagName(TagName.from(tagName))
                .build());
    }

    private QuestionHashtag findOrCreateQuestionHashtag(Question question, Hashtag hashtag) {
        return questionHashtagRepository.findByQuestionIdAndHashtagId(question.getId(), hashtag.getId())
            .orElse(QuestionHashtag.builder()
                .question(question)
                .hashtag(hashtag)
                .build());
    }

    public List<Long> findIdsByHashtagName(String hashtag) {
        Hashtag findHashtag = hashtagRepository.findByTagName(hashtag).orElseThrow(HashtagNotExistedException::new);
        return questionHashtagRepository.findQuestionIdsByHashtagId(findHashtag.getId());
    }
}
