package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.chat.exception.SessionIdAlreadyExistException;
import underdogs.devbie.chat.exception.SessionNotExistException;

class ChatSessionInformationsTest {

    @DisplayName("세션 추가")
    @Test
    void addSession() {
        ChatSessionInformations chatSessionInformations = new ChatSessionInformations();

        chatSessionInformations.addSessionInfo("1", 1L);
        assertThat(chatSessionInformations.countSessionOn(1L)).isEqualTo(1L);

        chatSessionInformations.addSessionInfo("2", 1L);
        assertThat(chatSessionInformations.countSessionOn(1L)).isEqualTo(2L);

        chatSessionInformations.addSessionInfo("3", 2L);
        assertThat(chatSessionInformations.countSessionOn(2L)).isEqualTo(1L);
    }

    @DisplayName("이미 있는 세션Id 추가")
    @Test
    void addAlreadyExistSessionId() {
        ChatSessionInformations chatSessionInformations = new ChatSessionInformations();
        chatSessionInformations.addSessionInfo("1", 1L);
        assertThatThrownBy(() -> chatSessionInformations.addSessionInfo("1", 2L))
            .isInstanceOf(SessionIdAlreadyExistException.class);
    }

    @DisplayName("세션 제거")
    @Test
    void removeSession() {
        ChatSessionInformations chatSessionInformations = new ChatSessionInformations();

        chatSessionInformations.addSessionInfo("1", 1L);
        assertThat(chatSessionInformations.countSessionOn(1L)).isEqualTo(1L);

        chatSessionInformations.addSessionInfo("2", 1L);
        assertThat(chatSessionInformations.countSessionOn(1L)).isEqualTo(2L);

        chatSessionInformations.removeSession("2");
        assertThat(chatSessionInformations.countSessionOn(1L)).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 세션 제거")
    @Test
    void removeNotExistSessionId() {
        ChatSessionInformations chatSessionInformations = new ChatSessionInformations();
        assertThatThrownBy(() -> chatSessionInformations.removeSession("1"))
            .isInstanceOf(SessionNotExistException.class);
    }

    @DisplayName("공고 별 세션 수 카운팅")
    @Test
    void countSessionOn() {
        ChatSessionInformations chatSessionInformations = new ChatSessionInformations();

        assertThat(chatSessionInformations.countSessionOn(1L)).isEqualTo(0);
    }
}