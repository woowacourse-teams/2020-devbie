package underdogs.devbie.favorite.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.favorite.service.QuestionFavoriteService;

@RestController
@RequestMapping("/api/favorite-question")
public class QuestionFavoriteController extends FavoriteController {

    public QuestionFavoriteController(QuestionFavoriteService questionFavoriteService) {
        this.favoriteService = questionFavoriteService;
    }
}
