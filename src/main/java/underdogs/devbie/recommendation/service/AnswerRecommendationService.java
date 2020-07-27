package underdogs.devbie.recommendation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.recommendation.domain.AnswerRecommendation;
import underdogs.devbie.recommendation.domain.AnswerRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerRecommendationService {

    private final AnswerRecommendationRepository answerRecommendations;

    public RecommendationResponse count(Long answerId) {
        List<AnswerRecommendation> recommendations = answerRecommendations.findByAnswerId(answerId);

        return RecommendationResponse.fromAnswerRecommendation(recommendations);
    }

    @Transactional
    public void createRecommendation(Long answerId, Long userId, RecommendationType recommendationType) {
        answerRecommendations.findByAnswerIdAndUserId(answerId, userId)
            .ifPresent(x -> new AlreadyExistException());

        answerRecommendations.save(AnswerRecommendation.of(answerId, userId, recommendationType));
    }

    @Transactional
    public void toggleRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        AnswerRecommendation answerRecommendation = answerRecommendations
            .findByAnswerIdAndUserId(questionId, userId)
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(recommendationType.toggleType()))
            .orElseThrow(NotExistException::new);

        answerRecommendation.toggleRecommended();
    }

    @Transactional
    public void deleteRecommendation(Long questionId, Long userId) {
        AnswerRecommendation answerRecommendation = answerRecommendations
            .findByAnswerIdAndUserId(questionId, userId)
            .orElseThrow(NotExistException::new);

        answerRecommendations.delete(answerRecommendation);
    }
}
