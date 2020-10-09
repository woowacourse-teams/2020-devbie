package underdogs.devbie.recommendation.domain;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRecommendationRepositoryImpl implements QuestionRecommendationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<QuestionRecommendation> findByObjectAndUserId(Long questionId, Long userId) {
        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(QQuestionRecommendation.questionRecommendation)
            .where(QQuestionRecommendation.questionRecommendation.questionId.eq(questionId),
                QQuestionRecommendation.questionRecommendation.userId.eq(userId))
            .fetchOne());
    }
}
