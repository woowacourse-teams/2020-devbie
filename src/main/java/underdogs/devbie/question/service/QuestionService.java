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
import underdogs.devbie.question.domain.QuestionRepository;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.dto.HashtagsRequest;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.exception.NotMatchedQuestionAuthorException;
import underdogs.devbie.question.exception.QuestionNotExistedException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final HashtagRepository hashtagRepository;
    private final QuestionHashtagRepository questionHashtagRepository;

    @Transactional
    public Long save(Long userId, QuestionCreateRequest request) {
        Question savedQuestion = questionRepository.save(request.toEntity(userId));
        return savedQuestion.getId();
    }

    public QuestionResponses readAll() {
        List<Question> questions = questionRepository.findAll();
        return QuestionResponses.from(questions);
    }

    @Transactional
    public QuestionResponse read(Long id) {
        Question question = readOne(id);
        question.increaseVisits();
        return QuestionResponse.from(question);
    }

    private Question readOne(Long questionId) {
        return questionRepository.findById(questionId)
            .orElseThrow(QuestionNotExistedException::new);
    }

    @Transactional
    public void update(Long userId, Long questionId, QuestionUpdateRequest request) {
        Question question = readOne(questionId);

        validateQuestionAuthor(userId, question);

        question.updateQuestionInfo(request.toEntity(userId));
    }

    private void validateQuestionAuthor(Long userId, Question question) {
        if (isNotAuthorOfQuestion(userId, question)) {
            throw new NotMatchedQuestionAuthorException();
        }
    }

    private boolean isNotAuthorOfQuestion(Long userId, Question question) {
        return !userId.equals(question.getUserId());
    }

    @Transactional
    public void delete(Long userId, Long questionId) {
        Question question = readOne(questionId);

        validateQuestionAuthor(userId, question);

        questionRepository.deleteById(questionId);
    }

    public QuestionResponses searchByTitle(String keyword) {
        List<Question> questions = questionRepository.findByTitleLike(keyword);
        return QuestionResponses.from(questions);
    }

    @Transactional
    public void saveOrUpdateHashtags(Long questionId, HashtagsRequest request) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(QuestionNotExistedException::new);

        Set<QuestionHashtag> hashtags = request.getHashtags()
            .stream()
            .map(tagName -> {
                Hashtag hashtag = findOrCreateHashtag(tagName);
                hashtagRepository.save(hashtag);
                QuestionHashtag questionHashtag = findOrCreateQuestionHashtag(question, hashtag);
                questionHashtagRepository.save(questionHashtag);
                return questionHashtag;
            })
            .collect(Collectors.toSet());

        question.setHashtags(hashtags);
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

    @Transactional
    public void deleteHashtag(Long questionId, Long hashtagId) {
        questionHashtagRepository.deleteByQuestionIdAndHashtagId(questionId, hashtagId);
    }
}
