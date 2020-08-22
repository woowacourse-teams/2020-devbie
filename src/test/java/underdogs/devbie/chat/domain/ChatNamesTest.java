package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ChatNamesTest {

    @Test
    void from() {
        assertThat(ChatNames.from(Collections.EMPTY_LIST)).isInstanceOf(ChatRoom.class);
    }

    @Test
    void fetchNonRedundantName() {
        ChatNames chatNames = ChatNames.from(new ArrayList<>());
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
        ChatNames chatNames = ChatNames.from(new ArrayList<>());
        chatNames.add(ChatName.of("이름1"));

        assertThat(chatNames.getChatNames()).hasSize(1);
    }

    @Test
    void delete() {
        ChatName chatName = ChatName.of("이름");
        ChatNames chatNames = ChatNames.from(new ArrayList<>(Arrays.asList(chatName)));
        chatNames.delete(chatName);

        assertThat(chatNames.getChatNames()).hasSize(0);
    }
}