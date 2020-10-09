package underdogs.devbie.chat.dto;

import underdogs.devbie.chat.domain.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
