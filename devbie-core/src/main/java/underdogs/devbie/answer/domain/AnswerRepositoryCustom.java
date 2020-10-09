package underdogs.devbie.answer.domain;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findByQuestionIdOrderByRecommendationCount(Long questionId);
}
