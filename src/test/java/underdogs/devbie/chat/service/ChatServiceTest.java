package underdogs.devbie.chat.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import underdogs.devbie.chat.domain.Chat;
import underdogs.devbie.chat.domain.ChatName;
import underdogs.devbie.chat.domain.ChatNames;
import underdogs.devbie.chat.domain.ChatRepository;
import underdogs.devbie.chat.domain.ChatRoom;
import underdogs.devbie.chat.domain.ChatRoomRepository;
import underdogs.devbie.chat.domain.TitleColor;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;
import underdogs.devbie.chat.dto.StompMessageResponse;

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
    void sendMessage() {
        Long noticeId = 1L;

        List<Chat> chats = Arrays.asList(
            Chat.of("말하는 원숭이", TitleColor.AMBER, "message1", ChatRoom.from(noticeId)),
            Chat.of("돌리는 사자", TitleColor.BAROSSA, "message2", ChatRoom.from(noticeId)),
            Chat.of("만지는 표범", TitleColor.DARK_ORCHID, "message3", ChatRoom.from(noticeId)));

        Set<ChatName> chatNames = new HashSet<>(Arrays.asList(
            ChatName.of("말하는 원숭이", TitleColor.AMBER),
            ChatName.of("돌리는 사자", TitleColor.BAROSSA),
            ChatName.of("만지는 표범", TitleColor.DARK_ORCHID)
        ));

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chats(chats)
            .chatNames(ChatNames.from(chatNames))
            .build();

        MessageSendRequest messageSendRequest = new MessageSendRequest(noticeId
            , "말하는 원숭이", "메세지", TitleColor.AMBER.getColor());

        given(chatRoomRepository.findByNoticeId(anyLong())).willReturn(Optional.of(chatRoom));
        given(chatRepository.save(any(Chat.class))).willReturn(
            Chat.of("말하는 원숭이", TitleColor.AMBER, "메세지", chatRoom));
        doNothing().when(simpMessagingTemplate).convertAndSend(any(String.class), any(StompMessageResponse.class));

        chatService.sendMessage(messageSendRequest);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(chatRepository).save(any());
        verify(simpMessagingTemplate).convertAndSend(any(), any(StompMessageResponse.class));
    }

    @DisplayName("noticeId에 해당하는 Chatroom이 존재할 경우 Chatroom 생성 하지 않음")
    @Test
    void connectWhenChatRoomDoesNotExist() {
        Long noticeId = 1L;

        List<Chat> chats = Arrays.asList(
            Chat.of("말하는 원숭이", TitleColor.AMBER, "message1", ChatRoom.from(noticeId)),
            Chat.of("돌리는 사자", TitleColor.BAROSSA, "message2", ChatRoom.from(noticeId)),
            Chat.of("만지는 표범", TitleColor.DARK_ORCHID, "message3", ChatRoom.from(noticeId)));

        Set<ChatName> chatNames = new HashSet<>(Arrays.asList(
            ChatName.of("말하는 원숭이", TitleColor.AMBER),
            ChatName.of("돌리는 사자", TitleColor.BAROSSA),
            ChatName.of("만지는 표범", TitleColor.DARK_ORCHID)
        ));

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chats(chats)
            .chatNames(ChatNames.from(chatNames))
            .build();

        given(chatRoomRepository.findByNoticeId(anyLong())).willReturn(Optional.of(chatRoom));

        ChatRoomResponse chatRoomResponse = chatService.connect(noticeId);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(chatRoomRepository, never()).save(any());
        verify(simpMessagingTemplate).convertAndSend(any(), any(StompMessageResponse.class));

        assertThat(chatRoomResponse).isNotNull();
        assertThat(chatRoomResponse.getMessageResponses()).isNotNull();
        List<MessageResponse> messageResponses = chatRoomResponse.getMessageResponses().getMessageResponses();
        assertAll(
            () -> assertEquals(messageResponses.get(0).getName(), "말하는 원숭이"),
            () -> assertEquals(messageResponses.get(1).getName(), "돌리는 사자"),
            () -> assertEquals(messageResponses.get(2).getName(), "만지는 표범"),
            () -> assertThat(chatRoomResponse.getHeadCount()).isEqualTo(4)
        );
    }

    @DisplayName("noticeId에 해당하는 Chatroom이 존재할 경우 Chatroom 생성")
    @Test
    void connectWhenChatRoomExist() {
        Long noticeId = 1L;

        List<Chat> chats = Arrays.asList(
            Chat.of("말하는 원숭이", TitleColor.AMBER, "message1", ChatRoom.from(noticeId)),
            Chat.of("돌리는 사자", TitleColor.BAROSSA, "message2", ChatRoom.from(noticeId)),
            Chat.of("만지는 표범", TitleColor.DARK_ORCHID, "message3", ChatRoom.from(noticeId)));

        Set<ChatName> chatNames = new HashSet<>(Arrays.asList(
            ChatName.of("말하는 원숭이", TitleColor.AMBER),
            ChatName.of("돌리는 사자", TitleColor.BAROSSA),
            ChatName.of("만지는 표범", TitleColor.DARK_ORCHID)
        ));

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chats(chats)
            .chatNames(ChatNames.from(chatNames))
            .build();

        given(chatRoomRepository.findByNoticeId(anyLong())).willReturn(Optional.of(chatRoom));

        ChatRoomResponse chatRoomResponse = chatService.connect(noticeId);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(simpMessagingTemplate).convertAndSend(any(), any(StompMessageResponse.class));

        assertThat(chatRoomResponse).isNotNull();
        assertThat(chatRoomResponse.getMessageResponses()).isNotNull();
        List<MessageResponse> messageResponses = chatRoomResponse.getMessageResponses().getMessageResponses();
        assertAll(
            () -> assertEquals(messageResponses.get(0).getName(), "말하는 원숭이"),
            () -> assertEquals(messageResponses.get(1).getName(), "돌리는 사자"),
            () -> assertEquals(messageResponses.get(2).getName(), "만지는 표범"),
            () -> assertThat(chatRoomResponse.getHeadCount()).isEqualTo(4)
        );
    }

    @DisplayName("NoticeId와 NickName으로 해당하는 채팅방 NickName 삭제하기")
    @Test
    void disconnect() {
        String nickName = "만지는 원숭이";
        Long noticeId = 1L;
        ChatName chatName = ChatName.of(nickName, TitleColor.AMBER);

        List<ChatName> chatNames = Collections.singletonList(chatName);

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chatNames(ChatNames.from(new HashSet<>(chatNames)))
            .build();

        given(chatRoomRepository.findByNoticeId(noticeId)).willReturn(Optional.of(chatRoom));

        chatService.disconnect(nickName, noticeId);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(simpMessagingTemplate).convertAndSend(anyString(), any(StompMessageResponse.class));
    }
}