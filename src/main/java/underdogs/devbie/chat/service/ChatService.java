package underdogs.devbie.chat.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.chat.domain.Chat;
import underdogs.devbie.chat.domain.ChatName;
import underdogs.devbie.chat.domain.ChatRepository;
import underdogs.devbie.chat.domain.ChatRoom;
import underdogs.devbie.chat.domain.ChatRoomRepository;
import underdogs.devbie.chat.domain.ChatSessionInformation;
import underdogs.devbie.chat.domain.StompMethodType;
import underdogs.devbie.chat.domain.TitleColor;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.chat.dto.StompMessageResponse;
import underdogs.devbie.exception.NotExistException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    public static final String PUBLISH_URL = "/channel/";

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatSessionInformation chatSessionInformation;

    @Transactional
    public void sendMessage(MessageSendRequest messageSendRequest) {
        ChatRoom chatRoom = getChatRoom(messageSendRequest.getNoticeId());

        Chat savedChat = saveChat(messageSendRequest, chatRoom);

        MessageResponse messageResponse = MessageResponse.from(savedChat);
        simpMessagingTemplate.convertAndSend(
            PUBLISH_URL + messageSendRequest.getNoticeId(),
            StompMessageResponse.of(StompMethodType.TALK, messageResponse));
    }

    private ChatRoom getChatRoom(Long noticeId) {
        return chatRoomRepository.findByNoticeId(noticeId)
            .orElseThrow(() -> new NotExistException(String.format("noticeId = %s 를 가진 채팅방이 존재하지 않습니다.", noticeId)));
    }

    private Chat saveChat(MessageSendRequest messageSendRequest, ChatRoom chatRoom) {
        Chat chat = Chat.of(
            messageSendRequest.getName(),
            TitleColor.from(messageSendRequest.getTitleColor()),
            messageSendRequest.getMessage(),
            chatRoom);
        return chatRepository.save(chat);
    }

    @Transactional
    public ChatRoomResponse connect(Long noticeId) {
        ChatRoom chatRoom = getOrCreateChatRoom(noticeId);

        ChatName name = chatRoom.addNewName();

        return ChatRoomResponse.of(
            chatRoom.getChats(), name.getChatName(), name.getColor(),
            chatSessionInformation.countSessionOn(noticeId));
    }

    private ChatRoom getOrCreateChatRoom(Long noticeId) {
        return chatRoomRepository.findByNoticeId(noticeId)
            .orElseGet(() -> chatRoomRepository.save(ChatRoom.from(noticeId)));
    }

    public void addNewSessionInfo(String sessionId, long noticeId) {
        chatSessionInformation.addSessionInfo(sessionId, noticeId);
        sendConnectionInformation(noticeId, StompMethodType.ENTER);
    }

    public void disconnectSession(String sessionId) {
        long noticeId = chatSessionInformation.removeSession(sessionId);
        sendConnectionInformation(noticeId, StompMethodType.QUIT);
    }

    private void sendConnectionInformation(Long noticeId, StompMethodType stompMethodType) {
        System.err.println("connection message sendded: " + stompMethodType.name());
        simpMessagingTemplate.convertAndSend(
            PUBLISH_URL + noticeId,
            StompMessageResponse.of(stompMethodType, null));
    }
}
