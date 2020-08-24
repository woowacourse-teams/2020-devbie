package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatMessageTest {

    @Test
    void from() {
        ChatMessage message = ChatMessage.from("메세지");
        assertAll(
            () -> assertThat(message).isInstanceOf(ChatMessage.class),
            () -> assertThat(message.getMessage()).isEqualTo("메세지")
        );
    }
}