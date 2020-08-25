package underdogs.devbie.chat.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
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

        ChatName nonOverlappingName = extractNonOverlappingName(chatNames);

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

    private static ChatName extractNonOverlappingName(Set<ChatName> chatNames) {
        int size = chatNames.size();
        if (size == 0) {
            throw new IndexOutOfBoundsException("컬렉션의 크기가 0입니다.");
        }
        int item = new Random().nextInt(size);
        int i = 0;
        for (ChatName chatName : chatNames) {
            if (i == item) {
                return chatName;
            }
            i++;
        }
        throw new IllegalArgumentException("도출될 수 없는 결과입니다.");
    }

    public static Set<ChatName> getNames() {
        return Collections.unmodifiableSet(names);
    }
}
