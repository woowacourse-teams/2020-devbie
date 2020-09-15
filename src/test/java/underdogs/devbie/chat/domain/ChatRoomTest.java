package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChatRoomTest {

    @Test
    void from() {
        assertThat(ChatRoom.from(1L)).isInstanceOf(ChatRoom.class);
    }

    // @DisplayName("중복되지 않은 ChatName 반환")
    // @Test
    // void addNewName() {
    //     List<ChatName> chatNames = Arrays.asList(
    //         ChatName.of("하늘하늘한 곰", TitleColor.AMBER),
    //         ChatName.of("찬란한 문어", TitleColor.BAROSSA),
    //         ChatName.of("어슴프레한 너구리", TitleColor.DARK_ORCHID),
    //         ChatName.of("발그레한 코끼리", TitleColor.DART_CYAN)
    //     );
    //     ChatRoom chatRoom = ChatRoom.builder()
    //         .noticeId(1L)
    //         .chatNames(ChatNames.from(new HashSet<>(chatNames)))
    //         .build();
    //
    //     Set<ChatName> existChatNames = new HashSet<>();
    //
    //     for (int i = 0; i < chatNames.size(); i++) {
    //         ChatName chatName = chatRoom.addNewName();
    //         assertThat(existChatNames.contains(chatName)).isFalse();
    //         existChatNames.add(chatName);
    //     }
    // }
    //
    // @DisplayName("ChatName 삭제")
    // @Test
    // void deleteChatName() {
    //     ChatName deleteChatName = ChatName.of("하늘하늘한 곰", TitleColor.AMBER);
    //     List<ChatName> chatNames = Arrays.asList(
    //         deleteChatName,
    //         ChatName.of("찬란한 문어", TitleColor.BAROSSA),
    //         ChatName.of("어슴프레한 너구리", TitleColor.DARK_ORCHID),
    //         ChatName.of("발그레한 코끼리", TitleColor.DART_CYAN)
    //     );
    //     ChatRoom chatRoom = ChatRoom.builder()
    //         .noticeId(1L)
    //         .chatNames(ChatNames.from(new HashSet<>(chatNames)))
    //         .build();
    //
    //     chatRoom.deleteChatName(deleteChatName.getChatName());
    //
    //     assertThat(chatRoom.getChatNames().getChatNames().contains(deleteChatName)).isFalse();
    // }
}
