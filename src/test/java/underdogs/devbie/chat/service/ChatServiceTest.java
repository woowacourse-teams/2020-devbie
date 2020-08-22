package underdogs.devbie.chat.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import underdogs.devbie.chat.dto.ChatRoomCreateRequest;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
import underdogs.devbie.chat.dto.MessageSendRequest;

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
            Chat.of("user0", "message1", ChatRoom.from(noticeId)),
            Chat.of("user1", "message2", ChatRoom.from(noticeId)),
            Chat.of("user2", "message3", ChatRoom.from(noticeId)));
        List<ChatName> chatNames = Arrays.asList(
            ChatName.of("말하는 원숭이"),
            ChatName.of("돌리는 사자"),
            ChatName.of("만지는 표범")
        );
        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chats(chats)
            .chatNames(ChatNames.from(new ArrayList(chatNames)))
            .build();
        MessageSendRequest messageSendRequest = new MessageSendRequest(noticeId
            , "말하는 원숭이", "메세지");

        given(chatRoomRepository.findByNoticeId(anyLong())).willReturn(Optional.of(chatRoom));
        given(chatRepository.save(any(Chat.class))).willReturn(Chat.of("말하는 원숭이", "메세지", chatRoom));
        doNothing().when(simpMessagingTemplate).convertAndSend(any(String.class), any(MessageResponse.class));

        chatService.sendMessage(messageSendRequest);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(chatRepository).save(any());
    }

    @DisplayName("noticeId에 해당하는 Chatroom이 존재할 경우 Chatroom 생성 하지 않음")
    @Test
    void noCreateIfExist() {
        Long noticeId = 1L;

        List<Chat> chats = Arrays.asList(
            Chat.of("user0", "message1", ChatRoom.from(noticeId)),
            Chat.of("user1", "message2", ChatRoom.from(noticeId)),
            Chat.of("user2", "message3", ChatRoom.from(noticeId)));

        List<ChatName> chatNames = Arrays.asList(
            ChatName.of("말하는 원숭이"),
            ChatName.of("돌리는 사자"),
            ChatName.of("만지는 표범")
        );

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chats(chats)
            .chatNames(ChatNames.from(new ArrayList(chatNames)))
            .build();

        given(chatRoomRepository.findByNoticeId(anyLong())).willReturn(Optional.of(chatRoom));

        ChatRoomResponse chatRoomResponse = chatService.createIfNotExist(new ChatRoomCreateRequest(noticeId));

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(chatRoomRepository, never()).save(any());

        assertThat(chatRoomResponse).isNotNull();
        assertThat(chatRoomResponse.getMessageResponses()).isNotNull();
        List<MessageResponse> messageResponses = chatRoomResponse.getMessageResponses().getMessageResponses();
        assertAll(
            () -> assertThat(chatRoomResponse.getNickName()).isNotBlank(),
            () -> assertEquals(messageResponses.get(0).getName(), "user0"),
            () -> assertEquals(messageResponses.get(1).getName(), "user1"),
            () -> assertEquals(messageResponses.get(2).getName(), "user2")
        );
    }

    @DisplayName("noticeId에 해당하는 Chatroom이 존재할 경우 Chatroom 생성")
    @Test
    void createIfExist() {
        Long noticeId = 1L;

        List<Chat> chats = Arrays.asList(
            Chat.of("user0", "message1", ChatRoom.from(noticeId)),
            Chat.of("user1", "message2", ChatRoom.from(noticeId)),
            Chat.of("user2", "message3", ChatRoom.from(noticeId)));

        List<ChatName> chatNames = Arrays.asList(
            ChatName.of("말하는 원숭이"),
            ChatName.of("돌리는 사자"),
            ChatName.of("만지는 표범")
        );

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chats(chats)
            .chatNames(ChatNames.from(new ArrayList(chatNames)))
            .build();

        given(chatRoomRepository.findByNoticeId(anyLong())).willReturn(Optional.of(chatRoom));

        ChatRoomResponse chatRoomResponse = chatService.createIfNotExist(new ChatRoomCreateRequest(noticeId));

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));

        assertThat(chatRoomResponse).isNotNull();
        assertThat(chatRoomResponse.getMessageResponses()).isNotNull();
        List<MessageResponse> messageResponses = chatRoomResponse.getMessageResponses().getMessageResponses();
        assertAll(
            () -> assertThat(chatRoomResponse.getNickName()).isNotBlank(),
            () -> assertEquals(messageResponses.get(0).getName(), "user0"),
            () -> assertEquals(messageResponses.get(1).getName(), "user1"),
            () -> assertEquals(messageResponses.get(2).getName(), "user2")
        );
    }

    @DisplayName("NoticeId와 NickName으로 해당하는 채팅방 NickName 삭제하기")
    @Test
    void deleteNickNameByNoticeId() {
        String nickName = "만지는 원숭이";
        Long noticeId = 1L;
        ChatName chatName = ChatName.of(nickName);

        List<ChatName> chatNames = Collections.singletonList(chatName);

        ChatRoom chatRoom = ChatRoom.builder()
            .noticeId(noticeId)
            .chatNames(ChatNames.from(new ArrayList(chatNames)))
            .build();

        ChatRoom chatRoomResult = ChatRoom.builder()
            .noticeId(noticeId)
            .build();

        given(chatRoomRepository.findByNoticeId(noticeId)).willReturn(Optional.of(chatRoom));
        given(chatRoomRepository.save(any(ChatRoom.class))).willReturn(chatRoomResult);

        chatService.deleteNickName(nickName, noticeId);

        verify(chatRoomRepository).findByNoticeId(eq(noticeId));
        verify(chatRoomRepository).save(eq(chatRoom));
    }
}