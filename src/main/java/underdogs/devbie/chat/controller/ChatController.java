package underdogs.devbie.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.chat.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/message")
    public void sendMessage(MessageSendRequest messageSendRequest) {
        System.out.println(messageSendRequest.getNoticeId());
        chatService.sendMessage(messageSendRequest);
    }
}
