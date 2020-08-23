package underdogs.devbie.chat.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.chat.domain.Chat;
import underdogs.devbie.chat.domain.ChatRoom;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.service.ChatService;
import underdogs.devbie.user.domain.User;

@WebMvcTest(controllers = ChatController.class)
class ChatControllerTest extends MvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ChatService chatService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .oauthId("TEST_USER")
            .email("TEST_EMAIL")
            .build();
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(user);
        given(loginUserArgumentResolver.supportsParameter(any())).willReturn(true);
    }

    @DisplayName("공고 ID로 채팅방 가져오기")
    @Test
    void fetchChatRoom() throws Exception {
        Long noticeId = 1L;

        ChatRoomResponse chatRoomResponse = ChatRoomResponse.of(
            Arrays.asList(
                Chat.of("user0", "message1", ChatRoom.from(noticeId)),
                Chat.of("user1", "message2", ChatRoom.from(noticeId)),
                Chat.of("user2", "message3", ChatRoom.from(noticeId))),
            "홍길동");
        given(chatService.fetchChatRoom(anyLong())).willReturn(chatRoomResponse);

        MvcResult result = getAction(String.format("/api/chats?noticeId=%s", 1L))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        ChatRoomResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
            ChatRoomResponse.class);

        assertAll(
            () -> assertEquals(resultResponse.getMessageResponses().getMessageResponses().size(), 3),
            () -> assertEquals(resultResponse.getMessageResponses().getMessageResponses().get(0).getName(), "user0"),
            () -> assertEquals(resultResponse.getMessageResponses().getMessageResponses().get(1).getName(), "user1"),
            () -> assertEquals(resultResponse.getMessageResponses().getMessageResponses().get(2).getName(), "user2"),
            () -> assertEquals(resultResponse.getNickName(), "홍길동")
        );
    }
}