package underdogs.devbie.chat.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import underdogs.devbie.chat.exception.SessionIdAlreadyExistException;
import underdogs.devbie.chat.exception.SessionNotExistException;

@Component
@NoArgsConstructor
public class ChatSessionInformation {
    private Map<String, Long> sessionNoticeIdMapping = new ConcurrentHashMap<>();

    public void addSessionInfo(String sessionId, Long noticeId) {
        if (sessionNoticeIdMapping.containsKey(sessionId)) {
            throw new SessionIdAlreadyExistException();
        }

        sessionNoticeIdMapping.put(sessionId, noticeId);
        System.err.println("added sessionId: " + sessionId);
        System.err.println("count and noticeId: " + countSessionOn(noticeId) + "////" + noticeId);
    }

    public long removeSession(String sessionId) {
        if (!sessionNoticeIdMapping.containsKey(sessionId)) {
            throw new SessionNotExistException();
        }

        long noticeId = sessionNoticeIdMapping.get(sessionId);
        sessionNoticeIdMapping.remove(sessionId);
        return noticeId;
    }

    public int countSessionOn(Long noticeId) {
        return (int)sessionNoticeIdMapping.values()
            .stream()
            .filter(notice -> notice.equals(noticeId))
            .count();
    }
}
