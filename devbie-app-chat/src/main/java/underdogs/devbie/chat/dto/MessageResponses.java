package underdogs.devbie.chat.dto;

import static java.util.stream.Collectors.*;

import java.util.List;

import underdogs.devbie.chat.domain.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MessageResponses {

    private List<MessageResponse> messageResponses;

    public static MessageResponses from(List<Chat> chats) {
        return new MessageResponses(chats.stream()
            .map(MessageResponse::from)
            .collect(toList()));
    }
}
