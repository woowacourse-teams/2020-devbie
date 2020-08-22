package underdogs.devbie.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @NoValidate
    @DeleteMapping("/api/chatrooms/{nickName}")
    public ResponseEntity<Void> deleteNickName(@PathVariable(name = "nickName") String nickName,
        @RequestParam(value = "noticeId") Long noticeId) {
        chatService.deleteNickName(nickName, noticeId);
        return ResponseEntity.noContent().build();
    }
}
