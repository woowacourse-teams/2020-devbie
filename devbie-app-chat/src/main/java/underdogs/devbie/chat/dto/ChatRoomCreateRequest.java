package underdogs.devbie.chat.dto;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Getter
public class ChatRoomCreateRequest {

    @NotNull(message = "Notice ID가 존재하지 않습니다.")
    private Long noticeId;
}
