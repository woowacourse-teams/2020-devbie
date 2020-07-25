package underdogs.devbie.recommendation.service;

import static underdogs.devbie.recommendation.domain.RecommendationType.*;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Service
@AllArgsConstructor
public class QuestionRecommendationService {

    private QuestionRecommendationRepository questionRecommendations;

    public RecommendationResponse count(Long questionId) {
        return new RecommendationResponse(
            questionRecommendations.countByQuestionIdAndAndRecommendationType(questionId, RECOMMENDED),
            questionRecommendations.countByQuestionIdAndAndRecommendationType(questionId, NON_RECOMMENDED));
    }

    public void createRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        questionRecommendations.findByQuestionIdAndUserId(questionId, userId)
            .ifPresent(x -> new AlreadyExistException());

        questionRecommendations.save(QuestionRecommendation.of(questionId, userId, recommendationType));
    }

    public void toggleRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        QuestionRecommendation questionRecommendation = questionRecommendations
            .findByQuestionIdAndUserId(questionId, userId)
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(recommendationType.toggleType()))
            .orElseThrow(NotExistException::new);

        questionRecommendation.toggleRecommended();

        // save 안 해주면 저장이 되지 않는다???
        questionRecommendations.save(questionRecommendation);
    }

    public void deleteRecommendation(Long questionId, Long userId) {
        QuestionRecommendation questionRecommendation = questionRecommendations
            .findByQuestionIdAndUserId(questionId, userId)
            .orElseThrow(NotExistException::new);

        questionRecommendations.delete(questionRecommendation);
    }
}
