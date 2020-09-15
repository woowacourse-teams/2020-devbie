package underdogs.devbie.chat.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import underdogs.devbie.chat.exception.SessionIdAlreadyExistException;
import underdogs.devbie.chat.exception.SessionNotExistException;

@Component
@NoArgsConstructor
public class ChatSessionInformation {
    private Map<String, String> sessionNoticeIdMapping = new HashMap<>();

    public void addSessionInfo(String sessionId, String noticeId) {
        if (sessionNoticeIdMapping.containsKey(sessionId)) {
            throw new SessionIdAlreadyExistException();
        }

        sessionNoticeIdMapping.put(sessionId, noticeId);
    }

    public void removeSession(String sessionId) {
        if (!sessionNoticeIdMapping.containsKey(sessionId)) {
            throw new SessionNotExistException();
        }

        sessionNoticeIdMapping.remove(sessionId);
    }

    public long countSessionOn(String noticeId) {
        return sessionNoticeIdMapping.values()
            .stream()
            .filter(notice -> notice.equals(noticeId))
            .count();
    }
}
