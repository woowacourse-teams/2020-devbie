package underdogs.devbie.recommendation.domain;

import static underdogs.devbie.recommendation.domain.QQuestionRecommendation.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRecommendationRepositoryImpl implements QuestionRecommendationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<QuestionRecommendation> findByObjectAndUserId(Long questionId, Long userId) {
        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(questionRecommendation)
            .where(questionRecommendation.questionId.eq(questionId),
                questionRecommendation.userId.eq(userId))
            .fetchOne());
    }
}
