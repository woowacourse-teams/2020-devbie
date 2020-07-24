package underdogs.devbie.answer.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import underdogs.devbie.answer.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
