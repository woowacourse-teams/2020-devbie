package underdogs.devbie.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.chat.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/message")
    public void sendMessage(MessageSendRequest messageSendRequest) {
        chatService.sendMessage(messageSendRequest);
    }

    @NoValidate
    @PatchMapping("/api/chatrooms")
    public ResponseEntity<ChatRoomResponse> fetchChatRoomInfo(@RequestParam("noticeId") Long noticeId) {
        return ResponseEntity.ok().body(chatService.fetchChatRoomInfo(noticeId));
    }

    @EventListener
    public void onSessionConnectedEvent(SessionConnectedEvent event) {
        String sessionId = extractSessionIdFrom(event);
        long noticeId = extractNoticeIdFrom(event);

        System.err.println("Connection with sessionId: " + sessionId);
        chatService.addNewSessionInfo(sessionId, noticeId);
        // System.err.println("sessionId: " + sessionId + System.lineSeparator() + "noticeId: " + noticeId);
    }

    @EventListener
    public void onSessionDisconnectedEvent(SessionDisconnectEvent event) {
        String sessionId = extractSessionIdFrom(event);

        System.err.println("DisConnection with sessionId: " + sessionId);
        chatService.disconnectSession(sessionId);
    }

    private String extractSessionIdFrom(AbstractSubProtocolEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        MessageHeaders messageHeaders = stompHeaderAccessor.getMessageHeaders();

        return (String)messageHeaders.get("simpSessionId");
    }

    private long extractNoticeIdFrom(AbstractSubProtocolEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        MessageHeaders messageHeaders = stompHeaderAccessor.getMessageHeaders();

        GenericMessage genericMessage = (GenericMessage)messageHeaders.get("simpConnectMessage");
        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>)genericMessage.getHeaders().get("nativeHeaders");

        return Long.parseLong(nativeHeaders.get("notice").get(0));
    }
}
