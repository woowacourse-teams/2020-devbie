package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.chat.exception.SessionIdAlreadyExistException;
import underdogs.devbie.chat.exception.SessionNotExistException;

class ChatSessionInformationTest {

    @DisplayName("세션 추가")
    @Test
    void addSession() {
        ChatSessionInformation chatSessionInformation = new ChatSessionInformation();

        chatSessionInformation.addSessionInfo("1", "1");
        assertThat(chatSessionInformation.countSessionOn("1")).isEqualTo(1L);

        chatSessionInformation.addSessionInfo("2", "1");
        assertThat(chatSessionInformation.countSessionOn("1")).isEqualTo(2L);

        chatSessionInformation.addSessionInfo("3", "2");
        assertThat(chatSessionInformation.countSessionOn("2")).isEqualTo(1L);
    }

    @DisplayName("이미 있는 세션Id 추가")
    @Test
    void addAlreadyExistSessionId() {
        ChatSessionInformation chatSessionInformation = new ChatSessionInformation();
        chatSessionInformation.addSessionInfo("1", "1");
        assertThatThrownBy(() -> chatSessionInformation.addSessionInfo("1", "2"))
            .isInstanceOf(SessionIdAlreadyExistException.class);
    }

    @DisplayName("세션 제거")
    @Test
    void removeSession() {
        ChatSessionInformation chatSessionInformation = new ChatSessionInformation();

        chatSessionInformation.addSessionInfo("1", "1");
        assertThat(chatSessionInformation.countSessionOn("1")).isEqualTo(1L);

        chatSessionInformation.addSessionInfo("2", "1");
        assertThat(chatSessionInformation.countSessionOn("1")).isEqualTo(2L);

        chatSessionInformation.removeSession("2");
        assertThat(chatSessionInformation.countSessionOn("1")).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 세션 제거")
    @Test
    void removeNotExistSessionId() {
        ChatSessionInformation chatSessionInformation = new ChatSessionInformation();
        assertThatThrownBy(() -> chatSessionInformation.removeSession("1"))
            .isInstanceOf(SessionNotExistException.class);
    }

    @DisplayName("공고 별 세션 수 카운팅")
    @Test
    void countSessionOn() {
        ChatSessionInformation chatSessionInformation = new ChatSessionInformation();

        assertThat(chatSessionInformation.countSessionOn("1")).isEqualTo(0L);
    }
}