package underdogs.devbie.chat.domain;

import java.util.HashSet;
import java.util.Set;

public class AllChatNames {

    private static final Set<ChatName> names = createAllChatNames();

    private static Set<ChatName> createAllChatNames() {
        Set<ChatName> chatNames = new HashSet<>();

        for (Verb verb : Verb.values()) {
            for (Noun noun : Noun.values()) {
                chatNames.add(ChatName.of(verb, noun));
            }
        }

        return chatNames;
    }

    public static ChatName fetchNonRedundantName(Set<ChatName> existNameInChatRoom) {
        Set<ChatName> chatNames = new HashSet<>();

        for (ChatName chatName : names) {
            chatNames.add(ChatName.of(chatName.getChatName()));
        }

        chatNames.removeAll(existNameInChatRoom);
        return chatNames.iterator().next();
    }
}
