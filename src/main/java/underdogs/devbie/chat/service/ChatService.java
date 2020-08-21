package underdogs.devbie.chat.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.chat.domain.Chat;
import underdogs.devbie.chat.domain.ChatRepository;
import underdogs.devbie.chat.domain.ChatRoom;
import underdogs.devbie.chat.domain.ChatRoomRepository;
import underdogs.devbie.chat.dto.ChatRoomCreateRequest;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    public static final String PUBLISH_URL = "/channel/";

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public void sendMessage(MessageSendRequest messageSendRequest) {
        ChatRoom chatRoom = getOrCreateChatRoom(messageSendRequest.getNoticeId());

        Chat savedChat = saveChat(messageSendRequest, chatRoom);

        simpMessagingTemplate.convertAndSend(PUBLISH_URL + messageSendRequest.getNoticeId(),
            MessageResponse.from(savedChat));
    }

    public ChatRoom getOrCreateChatRoom(Long noticeId) {
        return chatRoomRepository.findByNoticeId(noticeId)
            .orElseGet(() -> chatRoomRepository.save(ChatRoom.from(noticeId)));
    }

    private Chat saveChat(MessageSendRequest messageSendRequest, ChatRoom chatRoom) {
        Chat chat = Chat.of(messageSendRequest.getName(), messageSendRequest.getMessage(), chatRoom);
        return chatRepository.save(chat);
    }

    public ChatRoomResponse fetchChatRoom(Long noticeId) {
        ChatRoom chatRoom = getOrCreateChatRoom(noticeId);

        return ChatRoomResponse.of(chatRoom.getChats(), "홍길동");
    }

    public void createIfNotExist(ChatRoomCreateRequest chatRoomCreateRequest) {
        getOrCreateChatRoom(chatRoomCreateRequest.getNoticeId());
    }
}
