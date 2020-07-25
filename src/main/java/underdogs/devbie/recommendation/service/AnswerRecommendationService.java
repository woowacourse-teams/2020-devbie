package underdogs.devbie.recommendation.service;

import static underdogs.devbie.recommendation.domain.RecommendationType.*;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.recommendation.domain.AnswerRecommendation;
import underdogs.devbie.recommendation.domain.AnswerRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Service
@AllArgsConstructor
public class AnswerRecommendationService {

    private AnswerRecommendationRepository answerRecommendations;

    public RecommendationResponse count(Long answerId) {
        return new RecommendationResponse(
            answerRecommendations.countByAnswerIdAndAndRecommendationType(answerId, RECOMMENDED),
            answerRecommendations.countByAnswerIdAndAndRecommendationType(answerId, NON_RECOMMENDED));
    }

    public void createRecommendation(Long answerId, Long userId, RecommendationType recommendationType) {
        answerRecommendations.findByAnswerIdAndUserId(answerId, userId)
            .ifPresent(x -> new AlreadyExistException());

        answerRecommendations.save(AnswerRecommendation.of(answerId, userId, recommendationType));
    }

    public void toggleRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        AnswerRecommendation answerRecommendation = answerRecommendations
            .findByAnswerIdAndUserId(questionId, userId)
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(recommendationType.toggleType()))
            .orElseThrow(IllegalArgumentException::new);

        answerRecommendation.toggleRecommended();

        // save 안 해주면 저장이 되지 않는다???
        answerRecommendations.save(answerRecommendation);
    }

    public void deleteRecommendation(Long questionId, Long userId) {
        AnswerRecommendation answerRecommendation = answerRecommendations
            .findByAnswerIdAndUserId(questionId, userId)
            .orElseThrow(IllegalArgumentException::new);

        answerRecommendations.delete(answerRecommendation);
    }
}