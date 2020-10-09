package underdogs.devbie.answer.domain;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnswerRecommendationRepositoryImpl implements AnswerRecommendationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<AnswerRecommendation> findByObjectAndUserId(Long answerId, Long userId) {
        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(QAnswerRecommendation.answerRecommendation)
            .where(QAnswerRecommendation.answerRecommendation.answerId.eq(answerId),
                QAnswerRecommendation.answerRecommendation.userId.eq(userId))
            .fetchOne());
    }
}
