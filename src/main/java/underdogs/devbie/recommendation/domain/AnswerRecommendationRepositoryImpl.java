package underdogs.devbie.recommendation.domain;

import static underdogs.devbie.recommendation.domain.QAnswerRecommendation.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnswerRecommendationRepositoryImpl implements AnswerRecommendationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<AnswerRecommendation> findByObjectAndUserId(Long answerId, Long userId) {
        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(answerRecommendation)
            .where(answerRecommendation.answerId.eq(answerId),
                answerRecommendation.userId.eq(userId))
            .fetchOne());
    }
}
