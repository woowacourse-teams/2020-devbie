package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import underdogs.devbie.exception.NotExistException;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionRepository;
import underdogs.devbie.question.exception.QuestionNotExistedException;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@Service
public class QuestionRecommendationService extends RecommendationService {

    private QuestionRepository questionRepository;

    public QuestionRecommendationService(QuestionRecommendationRepository questionRecommendationRepository, QuestionRepository questionRepository) {
        this.recommendationRepository = questionRecommendationRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void createOrUpdateRecommendation(Long objectId, Long userId, RecommendationType recommendationType) {
        Optional<QuestionRecommendation> optionalQuestionRecommendation =
            recommendationRepository.findByObjectAndUserId(objectId, userId);

        QuestionRecommendation questionRecommendation =
            optionalQuestionRecommendation.orElse(QuestionRecommendation.of(objectId, userId, recommendationType));

        Question question = questionRepository.findById(objectId)
            .orElseThrow(QuestionNotExistedException::new);

        if (!questionRecommendation.hasRecommendationTypeOf(recommendationType)) {
            questionRecommendation.toggleRecommended();
            question.decreaseRecommendationCounts(recommendationType.toggleType());
        }

        recommendationRepository.save(questionRecommendation);
        question.increaseRecommendationCounts(recommendationType);
    }

    @Transactional
    public void deleteRecommendation(Long objectId, Long userId) {
        Optional<Recommendation> optRecommendation = recommendationRepository.findByObjectAndUserId(objectId, userId);

        Recommendation recommendation = optRecommendation.orElseThrow(NotExistException::new);

        recommendationRepository.delete(recommendation);

        Question question = questionRepository.findById(objectId)
            .orElseThrow(QuestionNotExistedException::new);
        question.decreaseRecommendationCounts(recommendation.getRecommendationType());
    }
}
