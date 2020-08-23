package underdogs.devbie.chat.domain;

import java.util.HashSet;
import java.util.Set;

public class ChatNameFactory {

    private static final Set<ChatName> names = createAllChatNames();

    private static Set<ChatName> createAllChatNames() {
        Set<ChatName> chatNames = new HashSet<>();

        for (Adjective adjective : Adjective.values()) {
            for (Noun noun : Noun.values()) {
                chatNames.add(ChatName.of(adjective, noun));
            }
        }

        return chatNames;
    }

    public static ChatName createNonOverlappingName(Set<ChatName> existNameInChatRoom) {
        Set<ChatName> chatNames = new HashSet<>();

        for (ChatName chatName : names) {
            chatNames.add(ChatName.of(chatName.getChatName()));
        }

        chatNames.removeAll(existNameInChatRoom);
        return chatNames.iterator().next();
    }
}
