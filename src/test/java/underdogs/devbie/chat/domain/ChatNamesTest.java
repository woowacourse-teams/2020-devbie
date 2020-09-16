package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class ChatNamesTest {

    @Test
    void from() {
        assertThat(ChatNames.from(Collections.EMPTY_SET)).isInstanceOf(ChatNames.class);
    }

    @Test
    void add() {
        ChatNames chatNames = ChatNames.from(new HashSet<>());
        chatNames.add(ChatName.of("하늘하늘한 곰", TitleColor.AMBER));

        assertThat(chatNames.getChatNames()).hasSize(1);
    }
}
