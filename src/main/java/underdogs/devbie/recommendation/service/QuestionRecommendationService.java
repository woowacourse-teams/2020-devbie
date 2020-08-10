package underdogs.devbie.recommendation.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionRepository;
import underdogs.devbie.question.exception.QuestionNotExistedException;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.QuestionRecommendationRepository;
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

        if (!questionRecommendation.hasRecommendationTypeOf(recommendationType)) {
            questionRecommendation.toggleRecommended();
        }

        recommendationRepository.save(questionRecommendation);

        Question question = questionRepository.findById(objectId)
            .orElseThrow(QuestionNotExistedException::new);
        question.increaseRecommendationCounts(recommendationType);
    }
}
