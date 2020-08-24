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
        chatNames.add(ChatName.from("이름1"));

        assertThat(chatNames.getChatNames()).hasSize(1);
    }

    @Test
    void delete() {
        ChatName chatName = ChatName.from("이름");
        ChatNames chatNames = ChatNames.from(new HashSet<>(Collections.singletonList(chatName)));
        chatNames.delete(chatName);

        assertThat(chatNames.getChatNames()).hasSize(0);
    }
}