package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.chat.domain.ChatName;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatNameResponse {

    private String name;

    public static ChatNameResponse from(ChatName chatName) {
        return new ChatNameResponse(chatName.getChatName());
    }
}
