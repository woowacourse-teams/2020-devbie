package underdogs.devbie.favorite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import underdogs.devbie.exception.BadRequestException;
import underdogs.devbie.favorite.domain.Favorite;
import underdogs.devbie.favorite.domain.QuestionFavorite;
import underdogs.devbie.favorite.domain.QuestionFavoriteRepository;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.service.QuestionService;

@Service
public class QuestionFavoriteService extends FavoriteService {

    private QuestionService questionService;

    public QuestionFavoriteService(QuestionFavoriteRepository questionFavoriteRepository,
        QuestionService questionService) {
        this.favoriteRepository = questionFavoriteRepository;
        this.questionService = questionService;
    }

    @Override
    public QuestionResponses findFavorites(Long userId) {
        List<Long> questionIds = favoriteRepository.findAllByUserId(userId);
        return questionService.findAllByIds(questionIds);
    }

    @Override
    public void createFavorite(Long objectId, Long userId) {
        Optional<Favorite> optionalFavorite = favoriteRepository.findByObjectAndUserId(objectId, userId);
        if (optionalFavorite.isPresent()) {
            throw new BadRequestException("이미 즐겨찾기 추가된 항목입니다.");
        }
        favoriteRepository.save(QuestionFavorite.of(objectId, userId));
    }
}
