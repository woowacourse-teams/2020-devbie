package underdogs.devbie.question.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.EsQuestion;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.repository.QuestionRepository;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionReadRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.exception.NotMatchedQuestionAuthorException;
import underdogs.devbie.question.exception.QuestionNotExistedException;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final UserService userService;
    private final QuestionHashtagService questionHashtagService;
    private final QuestionRepository questionRepository;

    private final ApplicationEventPublisher publisher;
    private final ElasticsearchOperations elasticsearchOperations;

    @Transactional
    public Long save(Long userId, QuestionCreateRequest request) {
        Question savedQuestion = questionRepository.save(request.toEntity(userId));
        questionHashtagService.saveHashtags(savedQuestion, request.getHashtags());

        publisher.publishEvent(new QuestionUpsertEvent(this, savedQuestion));
        return savedQuestion.getId();
    }

    public QuestionResponses readAll(QuestionReadRequest questionReadRequest, Pageable pageable) {
        Page<Question> questions = questionRepository.findAllBy(
            questionReadRequest.getTitle(),
            questionReadRequest.getContent(),
            pageable);
        return QuestionResponses.of(questions.getContent(), questions.getTotalPages());
    }

    @Transactional
    public QuestionResponse read(Long id, boolean isVisit) {
        Question question = readOne(id);
        if (isVisit) {
            question.increaseVisits();
        }
        User author = userService.findById(question.getUserId());
        return QuestionResponse.of(question, author);
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
        questionHashtagService.updateHashtags(question, request.getHashtags());

        publisher.publishEvent(new QuestionUpsertEvent(this, question));
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
    public void delete(User user, Long questionId) {
        Question question = readOne(questionId);

        validateQuestionAuthorOrAdmin(user, question);

        questionRepository.deleteById(questionId);

        publisher.publishEvent(new QuestionUpsertEvent(this, question));
    }

    private void validateQuestionAuthorOrAdmin(User user, Question question) {
        if (isNotAuthorOfQuestion(user.getId(), question) && user.isNotAdmin()) {
            throw new NotMatchedQuestionAuthorException();
        }
    }

    public QuestionResponses searchByHashtag(String hashtag) {
        List<Long> questionIds = questionHashtagService.findIdsByHashtagName(hashtag);
        List<Question> questions = questionRepository.findAllById(questionIds);
        return QuestionResponses.from(questions);
    }

    public QuestionResponses findAllByIds(List<Long> questionIds) {
        List<Question> questions = questionRepository.findAllById(questionIds);
        return QuestionResponses.from(questions);
    }

    public void toggleCount(Long questionId, RecommendationType recommendationType, boolean toggle) {
        Question question = readOne(questionId);
        if (toggle) {
            question.decreaseRecommendationCount(recommendationType.toggleType());
        }
        question.increaseRecommendationCount(recommendationType);
    }

    public void decreaseCount(Long questionId, RecommendationType recommendationType) {
        Question question = readOne(questionId);
        question.decreaseRecommendationCount(recommendationType);
    }

    public QuestionResponses search(String keyword) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(multiMatchQuery(keyword)
                .field("title")
                .field("content")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
            .build();
        SearchHits<EsQuestion> results = elasticsearchOperations.search(query, EsQuestion.class, IndexCoordinates.of("question"));
        return QuestionResponses.fromEsQuestion(results.get().map(SearchHit::getContent).collect(Collectors.toList()));
    }
}
