package underdogs.devbie.chat.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatNames {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "chat_name",
        joinColumns = @JoinColumn(name = "chatroom_id")
    )
    private Set<ChatName> chatNames = new HashSet<>();

    public static ChatNames from(Set<ChatName> chatNames) {
        return new ChatNames(chatNames);
    }

    public ChatName fetchNonRedundantName() {
        return ChatNameFactory.createNonOverlappingName(chatNames);
    }

    public void add(ChatName chatName) {
        chatNames.add(chatName);
    }

    public void delete(String chatName) {
        chatNames.removeIf(name -> chatName.equals(name.getChatName()));
    }

    public int size() {
        return chatNames.size();
    }
}
