package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatNameResponse implements StompMessageResponseData {

    private String name;
    private String color;

    public static ChatNameResponse of(String chatName, String color) {
        return new ChatNameResponse(chatName, color);
    }
}
