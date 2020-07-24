package underdogs.devbie.answer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.repository.AnswerRepository;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.user.domain.User;

@Service
@AllArgsConstructor
@Transactional(readOnly=true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Long save(User user, AnswerCreateRequest request) {
        Answer answer = request.toEntity(user.getId());
        return answerRepository.save(answer).getId();
    }
}
