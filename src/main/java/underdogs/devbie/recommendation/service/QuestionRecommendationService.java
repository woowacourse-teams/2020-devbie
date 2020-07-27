package underdogs.devbie.recommendation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionRecommendationService {

    private final QuestionRecommendationRepository questionRecommendations;

    public RecommendationResponse count(Long questionId) {
        List<QuestionRecommendation> recommendations = questionRecommendations.findByQuestionId(questionId);

        return RecommendationResponse.fromQuestionRecommendation(recommendations);
    }

    @Transactional
    public void createRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        questionRecommendations.findByQuestionIdAndUserId(questionId, userId)
            .ifPresent(x -> new AlreadyExistException());

        questionRecommendations.save(QuestionRecommendation.of(questionId, userId, recommendationType));
    }

    @Transactional
    public void toggleRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        QuestionRecommendation questionRecommendation = questionRecommendations
            .findByQuestionIdAndUserId(questionId, userId)
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(recommendationType.toggleType()))
            .orElseThrow(NotExistException::new);

        questionRecommendation.toggleRecommended();
    }

    @Transactional
    public void deleteRecommendation(Long questionId, Long userId) {
        QuestionRecommendation questionRecommendation = questionRecommendations
            .findByQuestionIdAndUserId(questionId, userId)
            .orElseThrow(NotExistException::new);

        questionRecommendations.delete(questionRecommendation);
    }
}
