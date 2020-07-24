package underdogs.devbie.answer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;
import underdogs.devbie.answer.domain.Answers;
import underdogs.devbie.answer.domain.repository.AnswerRepository;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.dto.AnswerUpdateRequest;
import underdogs.devbie.answer.exception.AnswerNotExistedException;
import underdogs.devbie.answer.exception.NotMatchedAnswerAuthorException;
import underdogs.devbie.user.domain.User;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Long save(User user, AnswerCreateRequest request) {
        Answer answer = request.toEntity(user.getId());
        return answerRepository.save(answer).getId();
    }

    public AnswerResponses readAll() {
        Answers answers = Answers.from(answerRepository.findAll());
        return AnswerResponses.from(answers);
    }

    @Transactional
    public void update(User user, Long id, AnswerUpdateRequest request) {
        Answer answer = readOne(id);

        validateAuthentication(user, answer);

        AnswerContent updateRequestedContent = AnswerContent.from(request.getContent());
        answer.updateContent(updateRequestedContent);
    }

    private void validateAuthentication(User user, Answer answer) {
        if (isNotAuthorOfQuestion(user, answer)) {
            throw new NotMatchedAnswerAuthorException();
        }
    }

    private boolean isNotAuthorOfQuestion(User user, Answer answer) {
        return answer.isNotMatched(user.getId());
    }

    private Answer readOne(Long id) {
        return answerRepository.findById(id)
            .orElseThrow(AnswerNotExistedException::new);
    }

    public AnswerResponse read(Long id) {
        return AnswerResponse.from(readOne(id));
    }

    @Transactional
    public void delete(User user, Long id) {
        Answer answer = readOne(id);

        validateAuthentication(user, answer);

        answerRepository.delete(answer);
    }
}
