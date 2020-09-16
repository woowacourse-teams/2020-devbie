package underdogs.devbie.chat.domain;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import underdogs.devbie.chat.exception.SessionIdAlreadyExistException;
import underdogs.devbie.chat.exception.SessionNotExistException;

@Component
@NoArgsConstructor
public class ChatSessionInformations {

    private Map<String, ChatSessionInformation> sessionInformations = new ConcurrentHashMap<>();

    public ChatSessionInformation addSessionInfo(String sessionId, Long noticeId) {
        if (sessionInformations.containsKey(sessionId)) {
            throw new SessionIdAlreadyExistException();
        }

        ChatName nonOverlappingName = ChatNameFactory.createNonOverlappingName(extractChatNames());
        ChatSessionInformation chatSessionInformation = new ChatSessionInformation(noticeId, nonOverlappingName);
        sessionInformations.put(sessionId, chatSessionInformation);
        return chatSessionInformation;
    }

    private ChatNames extractChatNames() {
        Set<ChatName> chatNames = sessionInformations.values()
            .stream()
            .map(ChatSessionInformation::getChatName)
            .collect(Collectors.toSet());

        return ChatNames.from(chatNames);
    }

    public ChatSessionInformation removeSession(String sessionId) {
        if (!sessionInformations.containsKey(sessionId)) {
            throw new SessionNotExistException();
        }

        ChatSessionInformation chatSessionInformation = sessionInformations.get(sessionId);
        sessionInformations.remove(sessionId);
        return chatSessionInformation;
    }

    public int countSessionOn(Long noticeId) {
        return (int)sessionInformations.values()
            .stream()
            .filter(info -> info.hasNoticeOf(noticeId))
            .count();
    }

    public ChatName findChatNameOfSessionId(String sessionId) {
        ChatSessionInformation chatSessionInformation = sessionInformations.get(sessionId);

        return chatSessionInformation.getChatName();
    }
}
