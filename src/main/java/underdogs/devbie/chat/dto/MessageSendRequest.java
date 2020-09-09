package underdogs.devbie.chat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MessageSendRequest {

    private Long noticeId;
    private String name;
    private String message;
    private String titleColor;
}
