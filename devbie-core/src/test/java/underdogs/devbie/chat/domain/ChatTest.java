package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatTest {

    @Test
    void of() {
        assertThat(Chat.of("하늘하늘한 동글", TitleColor.AMBER, "메세지", ChatRoom.from(1L))).isInstanceOf(Chat.class);
    }
}
