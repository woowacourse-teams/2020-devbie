package underdogs.devbie.recommendation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.recommendation.service.QuestionRecommendationService;

@RestController
@RequestMapping("/api/recommendation-question")
public class QuestionRecommendationController extends RecommendationController {

    public QuestionRecommendationController(QuestionRecommendationService questionRecommendationService) {
        this.recommendationService = questionRecommendationService;
    }
}
