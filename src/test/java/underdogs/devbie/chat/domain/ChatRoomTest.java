package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ChatRoomTest {

    @Test
    void from() {
        assertThat(ChatRoom.from(1L)).isInstanceOf(ChatRoom.class);
    }

    @Test
    void fetchNonRedundantName() {
        List<ChatName> chatNames = Arrays.asList(
            ChatName.of("이름1"),
            ChatName.of("이름2"),
            ChatName.of("이름3"),
            ChatName.of("이름4")
        );
        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(1L)
            .chatNames(ChatNames.from(new HashSet<>(chatNames)))
            .build();

        Set<ChatName> existChatNames = new HashSet<>();

        for (int i = 0; i < chatNames.size(); i++) {
            ChatName chatName = chatRoom.fetchNonRedundantName();
            assertThat(existChatNames.contains(chatName)).isFalse();
            existChatNames.add(chatName);
        }
    }

    @Test
    void deleteChatName() {
        ChatName deleteChatName = ChatName.of("이름1");
        List<ChatName> chatNames = Arrays.asList(
            deleteChatName,
            ChatName.of("이름2"),
            ChatName.of("이름3"),
            ChatName.of("이름4")
        );
        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(1L)
            .chatNames(ChatNames.from(new HashSet<>(chatNames)))
            .build();

        chatRoom.deleteChatName(deleteChatName);

        assertThat(chatRoom.getChatNames().getChatNames().contains(deleteChatName)).isFalse();
    }
}