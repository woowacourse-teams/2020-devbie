package underdogs.devbie.chat.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatNameFactory {

    private static final Set<ChatName> names = createAllChatNames();

    private static Set<ChatName> createAllChatNames() {
        Set<ChatName> chatNames = new HashSet<>();

        for (Adjective adjective : Adjective.values()) {
            for (Animal animal : Animal.values()) {
                chatNames.add(ChatName.of(adjective, animal, null));
            }
        }

        return chatNames;
    }

    public static ChatName createNonOverlappingName(ChatNames existNameInChatRoom) {
        Set<ChatName> chatNames = new HashSet<>();
        for (ChatName chatName : names) {
            chatNames.add(ChatName.of(chatName.getChatName(), null));
        }

        chatNames.removeAll(existNameInChatRoom.getChatNames());

        ChatName nonOverlappingName = chatNames.iterator().next();

        TitleColor nonOverlappingColor = createNonOverlappingColor(existNameInChatRoom.getChatNames());

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
