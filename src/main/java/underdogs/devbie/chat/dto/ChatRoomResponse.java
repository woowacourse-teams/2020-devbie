package underdogs.devbie.chat.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.chat.domain.Chat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ChatRoomResponse {

    private MessageResponses messageResponses;

    private String nickName;

    public static ChatRoomResponse of(List<Chat> chats, String nickName) {
        return new ChatRoomResponse(MessageResponses.from(chats), nickName);
    }
}
