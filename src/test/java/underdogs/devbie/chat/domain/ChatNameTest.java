package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatNameTest {

    @Test
    void ofByEnum() {
        ChatName chatName = ChatName.of(Adjective.하늘하늘한, Animal.곰, TitleColor.AMBER);

        assertAll(
            () -> assertThat(chatName).isNotNull(),
            () -> assertEquals(chatName.getChatName(), "하늘하늘한 곰"),
            () -> assertEquals(chatName.getColor(), TitleColor.AMBER)
        );
    }

    @Test
    void ofByString() {
        ChatName chatName = ChatName.of("하늘하늘한 곰", TitleColor.AMBER);

        assertAll(
            () -> assertThat(chatName).isNotNull(),
            () -> assertEquals(chatName.getChatName(), "하늘하늘한 곰"),
            () -> assertEquals(chatName.getColor(), TitleColor.AMBER)
        );
    }
}
