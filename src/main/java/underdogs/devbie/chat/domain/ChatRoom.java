package underdogs.devbie.chat.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    private Long noticeId;

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats = new ArrayList<>();

    @Embedded
    private ChatNames chatNames = new ChatNames();

    public static ChatRoom from(Long noticeId) {
        return ChatRoom.builder()
            .noticeId(noticeId)
            .chats(new ArrayList<>())
            .chatNames(new ChatNames())
            .build();
    }

    public ChatName addNewName() {
        ChatName chatName = ChatNameFactory.createNonOverlappingName(chatNames);
        chatNames.add(chatName);
        return chatName;
    }

    public ChatName deleteChatName(String chatName) {
        return chatNames.delete(chatName);
    }
}
