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
import underdogs.devbie.question.domain.QuestionHashtagRepository;
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
        questionHashtagRepository.deleteAllByHashtagIds(deleteTargetIds);
    }

    private QuestionHashtags mapToQuestionHashtags(Question question, Set<String> hashtags) {
        return QuestionHashtags.from(hashtags
                .stream()
                .map(tagName -> {
                    Hashtag hashtag = hashtagService.findOrCreateHashtag(tagName);
                    return findOrCreateQuestionHashtag(question, hashtag);
                })
                .collect(Collectors.toSet()));
    }

    private QuestionHashtag findOrCreateQuestionHashtag(Question question, Hashtag hashtag) {
        QuestionHashtag questionHashtag = questionHashtagRepository.findByQuestionIdAndHashtagId(question.getId(), hashtag.getId())
            .orElse(QuestionHashtag.builder()
                .question(question)
                .hashtag(hashtag)
                .build());
        return questionHashtagRepository.save(questionHashtag);
    }
}
