package underdogs.devbie.chat.service;

import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import underdogs.devbie.chat.domain.Chat;
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
        List<Chat> chats = Arrays.asList(
            Chat.of("user0", "message1", ChatRoom.from(noticeId)),
            Chat.of("user1", "message2", ChatRoom.from(noticeId)),
            Chat.of("user2", "message3", ChatRoom.from(noticeId))
        );
        given(chatRepository.findByNoticeId(noticeId)).willReturn(chats);
        chatService.readByNoticeId(noticeId);

        verify(chatRepository).findByNoticeId(eq(noticeId));
    }
}