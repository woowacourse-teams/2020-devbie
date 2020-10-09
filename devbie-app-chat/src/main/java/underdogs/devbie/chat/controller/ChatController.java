package underdogs.devbie.chat.controller;

import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.chat.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private static final String SIMP_SESSION_ID = "simpSessionId";
    private static final String SIMP_DESTINATION = "simpDestination";
    private static final String SIMP_DESTINATION_SEPARATOR = "/";
    private static final int NOTICE_ID_INDEX_HELPER = 1;

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
    public void onSessionSubscribeEvent(SessionSubscribeEvent event) {
        String sessionId = extractSessionIdFrom(event);
        long noticeId = extractNoticeIdFrom(event);

        chatService.addNewSessionInfo(sessionId, noticeId);
    }

    @EventListener
    public void onSessionDisconnectedEvent(SessionDisconnectEvent event) {
        String sessionId = extractSessionIdFrom(event);

        chatService.disconnectSession(sessionId);
    }

    private String extractSessionIdFrom(AbstractSubProtocolEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        MessageHeaders messageHeaders = stompHeaderAccessor.getMessageHeaders();

        return (String)messageHeaders.get(SIMP_SESSION_ID);
    }

    private long extractNoticeIdFrom(AbstractSubProtocolEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        MessageHeaders messageHeaders = stompHeaderAccessor.getMessageHeaders();

        String simpDestination = (String)messageHeaders.get(SIMP_DESTINATION);
        String[] splitted = simpDestination.split(SIMP_DESTINATION_SEPARATOR);
        return Long.parseLong(splitted[splitted.length - NOTICE_ID_INDEX_HELPER]);
    }
}
