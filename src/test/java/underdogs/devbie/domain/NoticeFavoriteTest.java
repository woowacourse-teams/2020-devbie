package underdogs.devbie.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.CreateFailException;
import underdogs.devbie.favorite.domain.NoticeFavorite;

class NoticeFavoriteTest {

    @DisplayName("NoticeFavorite 생성자 테스트 - noticeId 없을 때 예외 발생")
    @Test
    void noticeFavoriteInitialize_WithoutNoticeId() {
        assertThatThrownBy(() -> NoticeFavorite.of(null, 1L))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("NoticeFavorite 생성자 테스트 - userId 없을 때 예외 발생")
    @Test
    void noticeFavoriteInitialize_WithoutUserId() {
        assertThatThrownBy(() -> NoticeFavorite.of(1L, null))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("NoticeFavorite 생성자 테스트")
    @Test
    void noticeFavoriteInitialize() {
        assertThat(NoticeFavorite.of(1L, 1L))
            .isInstanceOf(NoticeFavorite.class);
    }
}
