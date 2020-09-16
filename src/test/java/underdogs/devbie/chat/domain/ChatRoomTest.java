package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatRoomTest {

    @Test
    void from() {
        assertThat(ChatRoom.from(1L)).isInstanceOf(ChatRoom.class);
    }
}
