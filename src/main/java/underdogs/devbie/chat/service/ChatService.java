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
import underdogs.devbie.chat.domain.ChatSessionInformations;
import underdogs.devbie.chat.domain.StompMethodType;
import underdogs.devbie.chat.domain.TitleColor;
import underdogs.devbie.chat.dto.ChatNameResponse;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.chat.dto.StompMessageResponse;
import exception.NotExistException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    public static final String PUBLISH_URL = "/channel/";

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatSessionInformations chatSessionInformations;

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
    public ChatRoomResponse fetchChatRoomInfo(Long noticeId) {
        ChatRoom chatRoom = getOrCreateChatRoom(noticeId);

        return ChatRoomResponse.of(chatRoom.getChats(), chatSessionInformations.countSessionOn(noticeId));
    }

    private ChatRoom getOrCreateChatRoom(Long noticeId) {
        return chatRoomRepository.findByNoticeId(noticeId)
            .orElseGet(() -> chatRoomRepository.save(ChatRoom.from(noticeId)));
    }

    public void addNewSessionInfo(String sessionId, long noticeId) {
        ChatSessionInformation chatSessionInformation = chatSessionInformations.addSessionInfo(sessionId, noticeId);
        sendConnectionInformation(StompMethodType.ENTER, chatSessionInformation, sessionId);
    }

    public void disconnectSession(String sessionId) {
        ChatSessionInformation chatSessionInformation = chatSessionInformations.removeSession(sessionId);
        sendConnectionInformation(StompMethodType.QUIT, chatSessionInformation, sessionId);
    }

    private void sendConnectionInformation(
        StompMethodType stompMethodType,
        ChatSessionInformation info,
        String sessionId
    ) {
        long noticeId = info.getNoticeId();
        ChatName chatName = info.getChatName();

        simpMessagingTemplate.convertAndSend(
            PUBLISH_URL + noticeId,
            StompMessageResponse.of(stompMethodType, ChatNameResponse.of(chatName, sessionId)));
    }
}
