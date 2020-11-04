package underdogs.devbie.favorite.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.exception.BadRequestException;
import underdogs.devbie.favorite.domain.QuestionFavorite;
import underdogs.devbie.favorite.domain.QuestionFavoriteRepository;
import underdogs.devbie.question.service.QuestionService;

@ExtendWith(MockitoExtension.class)
class QuestionFavoriteServiceTest {

    @Mock
    QuestionFavoriteRepository questionFavoriteRepository;
    @Mock
    QuestionService questionService;
    private QuestionFavoriteService questionFavoriteService;

    @BeforeEach
    void setUp() {
        questionFavoriteService = new QuestionFavoriteService(questionFavoriteRepository, questionService);
    }

    @DisplayName("질문 즐겨찾기 조회")
    @Test
    void findFavoriteNotices() {
        List<Long> questionIds = Lists.newArrayList(1L, 2L, 3L);
        given(questionFavoriteRepository.findAllByUserId(1L)).willReturn(questionIds);
        questionFavoriteService.findFavorites(1L);
        verify(questionService).findAllByIds(questionIds);
    }

    @DisplayName("질문 즐겨찾기 추가")
    @Test
    void createFavorite() {
        questionFavoriteService.createFavorite(1L, 1L);
    }

    @DisplayName("질문 즐겨찾기 중복 추가시 에외 발생")
    @Test
    void createFavorite_Throw_BadRequestException_When_DuplicatedFavoriteRequest() {
        given(questionFavoriteRepository.findByObjectAndUserId(anyLong(), anyLong()))
            .willReturn(Optional.of(QuestionFavorite.of(1L, 1L)));

        assertThatThrownBy(() -> {
            questionFavoriteService.createFavorite(1L, 1L);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("질문 즐겨찾기 삭제")
    @Test
    void deleteFavorite() {
        QuestionFavorite questionFavorite = QuestionFavorite.of(1L, 1L);
        given(questionFavoriteRepository.findByObjectAndUserId(anyLong(), anyLong()))
            .willReturn(Optional.of(questionFavorite));

        questionFavoriteService.deleteFavorite(1L, 1L);

        verify(questionFavoriteRepository).findByObjectAndUserId(anyLong(), anyLong());
    }
}
