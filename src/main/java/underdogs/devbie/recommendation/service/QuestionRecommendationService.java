package underdogs.devbie.recommendation.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.recommendation.dto.RecommendationResponse;

@Service
@AllArgsConstructor
public class QuestionRecommendationService {

    private QuestionRecommendationRepository questionRecommendationRepository;

    public RecommendationResponse count(Long questionId) {
        Long recommendedCount = questionRecommendationRepository.countByQuestionIdAndAndRecommendationType(questionId,
            RecommendationType.RECOMMENDED);
        Long nonRecommendedCount = questionRecommendationRepository.countByQuestionIdAndAndRecommendationType(
            questionId, RecommendationType.NON_RECOMMENDED);

        return new RecommendationResponse(recommendedCount, nonRecommendedCount);
    }

    public void createRecommendation(Long questionId, String recommendationType, Long userId) {
        validateNotExist(questionId, userId);

        questionRecommendationRepository.save(
            QuestionRecommendation.of(questionId, userId, RecommendationType.from(recommendationType)));
    }

    private void validateNotExist(Long questionId, Long userId) {
        questionRecommendationRepository.findByQuestionIdAndUserId(questionId, userId)
            .ifPresent(x -> {
                throw new AlreadyExistException();
            });
    }
}
