package underdogs.devbie.chat.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllChatNames {

    private static final List<ChatName> names = createAllChatNames();

    public static List<ChatName> createAllChatNames() {
        List<ChatName> chatNames = new ArrayList<>();

        for (Verb verb : Verb.values()) {
            for (Noun noun : Noun.values()) {
                chatNames.add(ChatName.of(verb, noun));
            }
        }

        return chatNames;
    }

    public static ChatName fetchNonRedundantName(List<ChatName> existNameInChatRoom) {
        List<ChatName> chatNames = new ArrayList<>();

        for (ChatName chatName : names) {
            chatNames.add(ChatName.of(chatName.getChatName()));
        }

        chatNames.removeAll(existNameInChatRoom);
        Collections.shuffle(chatNames);
        return chatNames.get(0);
    }
}
