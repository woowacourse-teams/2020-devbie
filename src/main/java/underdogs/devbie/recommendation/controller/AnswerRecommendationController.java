package underdogs.devbie.recommendation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.recommendation.service.AnswerRecommendationService;

@RestController
@RequestMapping("/api/recommendation-answer")
public class AnswerRecommendationController extends RecommendationController {

    public AnswerRecommendationController(AnswerRecommendationService answerRecommendationService) {
        this.recommendationService = answerRecommendationService;
    }
}
