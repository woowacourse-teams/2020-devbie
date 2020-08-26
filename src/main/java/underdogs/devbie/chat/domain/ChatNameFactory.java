package underdogs.devbie.chat.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
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
        if (set.size() == 0) {
            throw new IndexOutOfBoundsException("컬렉션의 크기가 0입니다.");
        }

        int randomIndex = new Random().nextInt(set.size());

        return set.stream()
            .skip(randomIndex)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("도출할 수 없는 결과입니다."));
    }

    public static Set<ChatName> getNames() {
        return Collections.unmodifiableSet(names);
    }
}
