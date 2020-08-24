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
import underdogs.devbie.chat.domain.TitleColor;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.exception.NotExistException;

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
        ChatRoom chatRoom = getChatRoom(messageSendRequest.getNoticeId());

        Chat savedChat = saveChat(messageSendRequest, chatRoom);

        simpMessagingTemplate.convertAndSend(PUBLISH_URL + messageSendRequest.getNoticeId(),
            MessageResponse.from(savedChat));
    }

    private ChatRoom getChatRoom(Long noticeId) {
        return chatRoomRepository.findByNoticeId(noticeId)
            .orElseThrow(() -> new NotExistException(String.format("noticeId = %s 를 가진 채팅방이 존재하지 않습니다.", noticeId)));
    }

    private Chat saveChat(MessageSendRequest messageSendRequest, ChatRoom chatRoom) {
        Chat chat = Chat.of(messageSendRequest.getName(), TitleColor.from(messageSendRequest.getTitleColor()),
            messageSendRequest.getMessage(), chatRoom);
        return chatRepository.save(chat);
    }

    @Transactional
    public ChatRoomResponse createIfNotExist(Long noticeId) {
        ChatRoom chatRoom = getOrCreateChatRoom(noticeId);

        ChatName name = chatRoom.fetchNonRedundantName();

        return ChatRoomResponse.of(chatRoom.getChats(), name.getChatName(), name.getColor().getColor());
    }

    private ChatRoom getOrCreateChatRoom(Long noticeId) {
        return chatRoomRepository.findByNoticeId(noticeId)
            .orElseGet(() -> chatRoomRepository.save(ChatRoom.from(noticeId)));
    }

    @Transactional
    public void deleteNickName(String nickName, Long noticeId) {
        ChatRoom chatRoom = getChatRoom(noticeId);
        chatRoom.deleteChatName(nickName);
    }
}
