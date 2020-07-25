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

    public void createRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        validateNotExist(questionId, userId);

        questionRecommendationRepository.save(QuestionRecommendation.of(questionId, userId, recommendationType));
    }

    private void validateNotExist(Long questionId, Long userId) {
        questionRecommendationRepository.findByQuestionIdAndUserId(questionId, userId)
            .ifPresent(x -> {
                throw new AlreadyExistException();
            });
    }

    public void toggleRecommendation(Long questionId, Long userId, RecommendationType recommendationType) {
        QuestionRecommendation questionRecommendation = questionRecommendationRepository
            .findByQuestionIdAndUserId(questionId, userId)
            .filter(recommendation -> recommendation.hasRecommendationTypeOf(recommendationType.toggleType()))
            .orElseThrow(IllegalArgumentException::new);

        questionRecommendation.toggleRecommended();

        // save 안 해주면 저장이 되지 않는다
        // 왜 그러지?
        questionRecommendationRepository.save(questionRecommendation);
    }

    public void deleteRecommendation(Long questionId, Long userId) {
        QuestionRecommendation questionRecommendation = questionRecommendationRepository
            .findByQuestionIdAndUserId(questionId, userId)
            .orElseThrow(IllegalArgumentException::new);

        questionRecommendationRepository.delete(questionRecommendation);
    }
}
