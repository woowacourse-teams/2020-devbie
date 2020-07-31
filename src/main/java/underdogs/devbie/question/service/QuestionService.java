package underdogs.devbie.question.service;

import java.util.List;

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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

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
}
