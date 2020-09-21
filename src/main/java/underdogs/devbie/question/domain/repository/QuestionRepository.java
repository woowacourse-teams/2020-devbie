package underdogs.devbie.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import underdogs.devbie.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
}
