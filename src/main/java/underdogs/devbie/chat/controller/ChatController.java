package underdogs.devbie.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.chat.dto.ChatRoomCreateRequest;
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
    public ResponseEntity<ChatRoomResponse> createIfNotExist(@RequestBody ChatRoomCreateRequest chatRoomCreateRequest) {
        return ResponseEntity.ok().body(chatService.createIfNotExist(chatRoomCreateRequest));
    }
}
