package underdogs.devbie.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.CreateFailException;
import underdogs.devbie.favorite.domain.QuestionFavorite;

class QuestionFavoriteTest {

    @DisplayName("QuestionFavorite 생성자 테스트 - questionId 없을 때 예외 발생")
    @Test
    void questionFavoriteInitialize_WithoutQuestionId() {
        assertThatThrownBy(() -> QuestionFavorite.of(null, 1L))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("QuestionFavorite 생성자 테스트 - userId 없을 때 예외 발생")
    @Test
    void questionFavoriteInitialize_WithoutUserId() {
        assertThatThrownBy(() -> QuestionFavorite.of(1L, null))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("QuestionFavorite 생성자 테스트")
    @Test
    void questionFavoriteInitialize() {
        assertThat(QuestionFavorite.of(1L, 1L))
            .isInstanceOf(QuestionFavorite.class);
    }
}
