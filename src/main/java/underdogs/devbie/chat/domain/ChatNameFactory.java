package underdogs.devbie.chat.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatNameFactory {

    private static final Set<ChatName> names = createAllChatNames();

    private static Set<ChatName> createAllChatNames() {
        Set<ChatName> chatNames = new HashSet<>();

        for (Adjective adjective : Adjective.values()) {
            for (Noun noun : Noun.values()) {
                chatNames.add(ChatName.of(adjective, noun, null));
            }
        }

        return chatNames;
    }

    public static ChatName createNonOverlappingName(Set<ChatName> existNameInChatRoom) {
        Set<ChatName> chatNames = new HashSet<>();
        for (ChatName chatName : names) {
            chatNames.add(ChatName.of(chatName.getChatName(), null));
        }

        chatNames.removeAll(existNameInChatRoom);

        ChatName nonOverlappingName = chatNames.iterator().next();

        TitleColor nonOverlappingColor = createNonOverlappingColor(existNameInChatRoom);

        nonOverlappingName.setColor(nonOverlappingColor);

        return nonOverlappingName;
    }

    private static TitleColor createNonOverlappingColor(Set<ChatName> existNameInChatRoom) {
        Set<TitleColor> existColorInChatRoom = existNameInChatRoom.stream()
            .map(ChatName::getColor)
            .collect(Collectors.toSet());

        return TitleColorFactory.createNonOverlappingColor(existColorInChatRoom);
    }
}
