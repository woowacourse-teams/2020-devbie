package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatSessionInformationTest {

    @Test
    void hasNoticeOf() {
        ChatSessionInformation chatSessionInformation =
            new ChatSessionInformation(1L, ChatName.of("testName", TitleColor.AMBER));

        assertThat(chatSessionInformation.hasNoticeOf(1L)).isTrue();
    }
}
