package underdogs.devbie.chat.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatNames {

    private Set<ChatName> chatNames = new HashSet<>();

    public static ChatNames from(Set<ChatName> chatNames) {
        return new ChatNames(chatNames);
    }

    public void add(ChatName chatName) {
        chatNames.add(chatName);
    }

    public Set<ChatName> getChatNames() {
        return Collections.unmodifiableSet(chatNames);
    }
}
