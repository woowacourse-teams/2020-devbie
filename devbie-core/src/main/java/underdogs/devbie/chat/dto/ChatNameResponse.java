package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.chat.domain.ChatName;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatNameResponse implements StompMessageResponseData {

    private String name;
    private String color;
    private String sessionId;

    public static ChatNameResponse of(ChatName chatName, String sessionId) {
        return new ChatNameResponse(chatName.getChatName(), chatName.getColor().getColor(), sessionId);
    }
}
