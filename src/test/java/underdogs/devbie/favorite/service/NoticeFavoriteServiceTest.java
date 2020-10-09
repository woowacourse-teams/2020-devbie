package underdogs.devbie.favorite.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exception.BadRequestException;
import underdogs.devbie.favorite.domain.NoticeFavorite;
import underdogs.devbie.favorite.domain.NoticeFavoriteRepository;
import underdogs.devbie.notice.service.NoticeService;
import underdogs.devbie.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class NoticeFavoriteServiceTest {

    @Mock
    NoticeFavoriteRepository noticeFavoriteRepository;
    @Mock
    UserService userService;
    @Mock
    NoticeService noticeService;
    private NoticeFavoriteService noticeFavoriteService;

    @BeforeEach
    void setUp() {
        noticeFavoriteService = new NoticeFavoriteService(noticeFavoriteRepository, userService, noticeService);
    }

    @DisplayName("공고 즐겨찾기 조회")
    @Test
    void findFavoriteNotices() {
        List<Long> noticeIds = Arrays.asList(1L, 2L, 3L);
        given(noticeFavoriteRepository.findAllByUserId(1L)).willReturn(noticeIds);
        noticeFavoriteService.findFavorites(1L);
        verify(noticeService).findAllByIds(noticeIds);
    }

    @DisplayName("공고 즐겨찾기 추가")
    @Test
    void createFavorite() {
        noticeFavoriteService.createFavorite(1L, 1L);
    }

    @DisplayName("공고 즐겨찾기 중복 추가시 에외 발생")
    @Test
    void createFavorite_Throw_BadRequestException_When_DuplicatedFavoriteRequest() {
        given(noticeFavoriteRepository.findByObjectAndUserId(anyLong(), anyLong()))
            .willReturn(Optional.of(NoticeFavorite.of(1L, 1L)));

        assertThatThrownBy(() -> {
            noticeFavoriteService.createFavorite(1L, 1L);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("공고 즐겨찾기 삭제")
    @Test
    void deleteFavorite() {
        NoticeFavorite favorite = NoticeFavorite.of(1L, 1L);
        given(noticeFavoriteRepository.findByObjectAndUserId(anyLong(), anyLong()))
            .willReturn(Optional.of(favorite));

        noticeFavoriteService.deleteFavorite(1L, 1L);

        verify(noticeFavoriteRepository).findByObjectAndUserId(anyLong(), anyLong());
    }
}
