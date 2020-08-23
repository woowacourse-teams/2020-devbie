package underdogs.devbie.chat.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import underdogs.devbie.chat.domain.ChatRepository;
import underdogs.devbie.chat.domain.ChatRoom;
import underdogs.devbie.chat.domain.ChatRoomRepository;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    private ChatService chatService;

    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    ChatRepository chatRepository;

    @Mock
    SimpMessagingTemplate simpMessagingTemplate;

    @BeforeEach
    void setUp() {
        this.chatService = new ChatService(chatRoomRepository, chatRepository, simpMessagingTemplate);
    }

    @Test
    void readByNoticeId() {
        Long noticeId = 1L;
        Optional<ChatRoom> optionalChatRoom = Optional.of(ChatRoom.from(1L));
        given(chatRoomRepository.findByNoticeId(noticeId)).willReturn(optionalChatRoom);
        chatService.fetchChatRoom(noticeId);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
    }
}