package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ChatNamesTest {

    @Test
    void from() {
        assertThat(ChatNames.from(Collections.EMPTY_SET)).isInstanceOf(ChatNames.class);
    }

    @Test
    void fetchNonRedundantName() {
        ChatNames chatNames = ChatNames.from(new HashSet<>());
        Set<ChatName> existChatNames = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            ChatName chatName = chatNames.fetchNonRedundantName();
            assertThat(existChatNames.contains(chatName)).isFalse();
            existChatNames.add(chatName);
            chatNames.add(chatName);
        }
    }

    @Test
    void add() {
        ChatNames chatNames = ChatNames.from(new HashSet<>());
        chatNames.add(ChatName.of("이름1", TitleColor.AMBER));

        assertThat(chatNames.getChatNames()).hasSize(1);
    }

    @Test
    void delete() {
        ChatName chatName = ChatName.of("이름", TitleColor.AMBER);
        ChatNames chatNames = ChatNames.from(new HashSet<>(Collections.singletonList(chatName)));
        chatNames.delete(chatName.getChatName());

        assertThat(chatNames.getChatNames()).hasSize(0);
    }
}