package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatNameTest {

    @Test
    void ofByEnum() {
        ChatName chatName = ChatName.of(Verb.돌리는, Noun.원숭이);

        assertAll(
            () -> assertThat(chatName).isNotNull(),
            () -> assertEquals(chatName.getChatName(), "돌리는 원숭이")
        );
    }

    @Test
    void ofByString() {
        ChatName chatName = ChatName.of("돌리는 원숭이");

        assertAll(
            () -> assertThat(chatName).isNotNull(),
            () -> assertEquals(chatName.getChatName(), "돌리는 원숭이")
        );
    }
}