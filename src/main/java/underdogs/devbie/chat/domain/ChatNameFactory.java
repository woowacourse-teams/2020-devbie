package underdogs.devbie.chat.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatNameFactory {

    private static final Set<ChatName> names = createAllChatNames();
    private static final Set<TitleColor> colors = new HashSet<>(Arrays.asList(TitleColor.values()));

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
        Set<ChatName> chatNames = removeExistNameInChatRoom(existNameInChatRoom);

        ChatName nonOverlappingName = extractRandom(chatNames);

        nonOverlappingName.setColor(extractRandom(colors));

        return nonOverlappingName;
    }

    private static Set<ChatName> removeExistNameInChatRoom(ChatNames existNameInChatRoom) {
        Set<ChatName> chatNames = new HashSet<>();
        for (ChatName chatName : names) {
            chatNames.add(ChatName.of(chatName.getChatName(), null));
        }

        chatNames.removeAll(existNameInChatRoom.getChatNames());
        return chatNames;
    }

    private static <T> T extractRandom(Set<T> set) {
        int randomNumber = (int)(Math.random() * set.size());

        return set.stream()
            .skip(randomNumber)
            .findFirst()
            .orElseThrow(() -> new IndexOutOfBoundsException("Collection 이 비어있습니다."));
    }

    public static Set<ChatName> getNames() {
        return Collections.unmodifiableSet(names);
    }
}
