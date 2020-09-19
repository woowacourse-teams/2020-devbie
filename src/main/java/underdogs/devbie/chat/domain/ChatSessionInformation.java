package underdogs.devbie.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChatSessionInformation {

    private final long noticeId;
    private final ChatName chatName;

    public boolean hasNoticeOf(long noticeId) {
        return this.noticeId == noticeId;
    }
}
