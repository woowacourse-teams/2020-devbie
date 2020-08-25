package underdogs.devbie.question.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionRepository;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.exception.NotMatchedQuestionAuthorException;
import underdogs.devbie.question.exception.QuestionNotExistedException;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final UserService userService;
    private final QuestionHashtagService questionHashtagService;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long save(Long userId, QuestionCreateRequest request) {
        Question savedQuestion = questionRepository.save(request.toEntity(userId));
        questionHashtagService.saveHashtags(savedQuestion, request.getHashtags());
        return savedQuestion.getId();
    }

    public QuestionResponses readAll(Pageable pageable) {
        Page<Question> questions = questionRepository.findAllBy(pageable);
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

    public QuestionResponses searchQuestionBy(String title, String content) {
        return QuestionResponses.from(questionRepository.findByBothLike(title, content));
    }

    public QuestionResponses searchByHashtag(String hashtag) {
        List<Long> questionIds = questionHashtagService.findIdsByHashtagName(hashtag);
        List<Question> questions = questionRepository.findAllById(questionIds);
        return QuestionResponses.from(questions);
    }
}
