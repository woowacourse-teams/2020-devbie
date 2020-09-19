package underdogs.devbie.question.domain.repository;

import java.util.List;

public interface QuestionHashtagRepositoryCustom {

    void deleteAllByHashtagIds(List<Long> ids);

    List<Long> findQuestionIdsByHashtagId(Long hashtagId);
}
