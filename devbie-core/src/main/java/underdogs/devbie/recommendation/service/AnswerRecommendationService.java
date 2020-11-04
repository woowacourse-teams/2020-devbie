package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.answer.service.AnswerService;
import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.recommendation.domain.AnswerRecommendation;
import underdogs.devbie.recommendation.domain.AnswerRecommendationRepository;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
public class AnswerRecommendationService extends RecommendationService {

    private final AnswerService answerService;

    public AnswerRecommendationService(
        AnswerRecommendationRepository answerRecommendationRepository,
        AnswerService answerService
    ) {
        this.recommendationRepository = answerRecommendationRepository;
        this.answerService = answerService;

    }

    @Override
    public void createOrUpdateRecommendation(Long answerId, Long userId, RecommendationType recommendationType) {
        Optional<AnswerRecommendation> optionalAnswerRecommendation =
            recommendationRepository.findByObjectAndUserId(answerId, userId);

        AnswerRecommendation answerRecommendation =
            optionalAnswerRecommendation.orElse(AnswerRecommendation.of(answerId, userId, recommendationType));

        boolean toggle = false;
        if (!answerRecommendation.hasRecommendationTypeOf(recommendationType)) {
            answerRecommendation.toggleRecommended();
            toggle = true;
        }
        recommendationRepository.save(answerRecommendation);
        answerService.toggleCount(answerId, recommendationType, toggle);
    }

    @Transactional
    public void deleteRecommendation(Long answerId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(answerId, userId);

        Recommendation recommendation = optRecommendation.orElseThrow(() -> new NotExistException("Recommendation"));

        recommendationRepository.delete(recommendation);
        answerService.decreaseCount(answerId, recommendation.getRecommendationType());
    }
}
