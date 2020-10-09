package underdogs.devbie.chat.dto;

import java.util.List;

import underdogs.devbie.chat.domain.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ChatRoomResponse {

    private MessageResponses messageResponses;

    private Integer headCount;

    public static ChatRoomResponse of(List<Chat> chats, Integer headCount) {
        return new ChatRoomResponse(MessageResponses.from(chats), headCount);
    }
}
