package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.chat.domain.Chat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MessageResponse {

    private Long id;
    private String name;
    private String message;

    public static MessageResponse from(Chat chat) {
        return new MessageResponse(chat.getId(), chat.getName().getChatName(), chat.getMessage().getMessage());
    }
}
