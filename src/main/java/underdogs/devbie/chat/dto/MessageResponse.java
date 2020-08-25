package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.chat.domain.Chat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MessageResponse implements StompMessageResponseData {

    private Long id;
    private String name;
    private String titleColor;
    private String message;

    public static MessageResponse from(Chat chat) {
        return new MessageResponse(chat.getId(), chat.getName().getChatName(), chat.getName().getColor().getColor(),
            chat.getMessage().getMessage());
    }
}
