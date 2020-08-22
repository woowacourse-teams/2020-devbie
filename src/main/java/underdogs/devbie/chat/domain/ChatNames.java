package underdogs.devbie.chat.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private List<ChatName> chatNames = new LinkedList<>();

    public static ChatNames from(List<ChatName> chatNames) {
        return new ChatNames(chatNames);
    }

    public ChatName fetchNonRedundantName() {
        return AllChatNames.fetchNonRedundantName(chatNames);
    }

    public void add(ChatName chatName) {
        chatNames.add(chatName);
    }
}
