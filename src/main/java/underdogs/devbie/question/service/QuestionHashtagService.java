package underdogs.devbie.question.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.repository.QuestionHashtagRepository;
import underdogs.devbie.question.domain.QuestionHashtags;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionHashtagService {

    private final HashtagService hashtagService;
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
        questionHashtagRepository.deleteAllByHashtagIds(deleteTargetIds, question.getId());
    }

    private QuestionHashtags mapToQuestionHashtags(Question question, Set<String> hashtags) {
        Set<QuestionHashtag> questionHashtags = hashtags.stream()
            .map(tagName -> {
                Hashtag hashtag = hashtagService.findOrCreateHashtag(tagName);
                return findOrCreateQuestionHashtag(question, hashtag);
            })
            .collect(Collectors.toSet());
        return QuestionHashtags.from(questionHashtags);
    }

    private QuestionHashtag findOrCreateQuestionHashtag(Question question, Hashtag hashtag) {
        QuestionHashtag questionHashtag = questionHashtagRepository.findByQuestionIdAndHashtagId(question.getId(), hashtag.getId())
            .orElse(QuestionHashtag.builder()
                .question(question)
                .hashtag(hashtag)
                .build());
        return questionHashtagRepository.save(questionHashtag);
    }

    public List<Long> findIdsByHashtagName(String hashtag) {
        Hashtag findHashtag = hashtagService.findOrCreateHashtag(hashtag);
        return questionHashtagRepository.findQuestionIdsByHashtagId(findHashtag.getId());
    }
}
