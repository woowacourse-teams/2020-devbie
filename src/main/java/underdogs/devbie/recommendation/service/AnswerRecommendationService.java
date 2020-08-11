package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.repository.AnswerRepository;
import underdogs.devbie.answer.exception.AnswerNotExistedException;
import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.question.exception.QuestionNotExistedException;
import underdogs.devbie.recommendation.domain.AnswerRecommendation;
import underdogs.devbie.recommendation.domain.AnswerRecommendationRepository;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
public class AnswerRecommendationService extends RecommendationService {

    private AnswerRepository answerRepository;

    public AnswerRecommendationService(AnswerRecommendationRepository answerRecommendationRepository, AnswerRepository answerRepository) {
        this.recommendationRepository = answerRecommendationRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void createOrUpdateRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        Optional<AnswerRecommendation> optionalAnswerRecommendation =
            recommendationRepository.findByObjectAndUserId(objectId, userId);

        AnswerRecommendation answerRecommendation =
            optionalAnswerRecommendation.orElse(AnswerRecommendation.of(objectId, userId, recommendationType));

        Answer answer = answerRepository.findById(objectId)
            .orElseThrow(AnswerNotExistedException::new);

        if (!answerRecommendation.hasRecommendationTypeOf(recommendationType)) {
            answerRecommendation.toggleRecommended();
            answer.decreaseRecommendationCounts(recommendationType.toggleType());
        }

        recommendationRepository.save(answerRecommendation);
        answer.increaseRecommendationCounts(recommendationType);
    }

    @Transactional
    public void deleteRecommendation(Long objectId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(objectId, userId);

        Recommendation recommendation = optRecommendation.orElseThrow(NotExistException::new);

        recommendationRepository.delete(recommendation);

        Answer answer = answerRepository.findById(objectId)
            .orElseThrow(QuestionNotExistedException::new);
        answer.decreaseRecommendationCounts(recommendation.getRecommendationType());
    }
}
