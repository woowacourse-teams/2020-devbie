package underdogs.devbie.chat.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

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
import underdogs.devbie.chat.dto.ChatRoomCreateRequest;
import underdogs.devbie.chat.dto.ChatRoomResponse;
import underdogs.devbie.chat.dto.MessageResponse;
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

    @DisplayName("Notice ID에 매칭되는 ChatRoom이 없을 경우 ChatRoom 생성 후 ChatRoomResponse 반환")
    @Test
    void createChatRoom() throws Exception {
        Long noticeId = 1L;
        ChatRoomCreateRequest chatRoomCreateRequest = new ChatRoomCreateRequest(noticeId);
        ChatRoomResponse chatRoomResponse = ChatRoomResponse.of(
            Arrays.asList(
                Chat.of("user0", "message1", ChatRoom.from(noticeId)),
                Chat.of("user1", "message2", ChatRoom.from(noticeId)),
                Chat.of("user2", "message3", ChatRoom.from(noticeId))),
            "홍길동");

        given(chatService.createIfNotExist(any())).willReturn(chatRoomResponse);

        MvcResult mvcResult = patchAction("/api/chatrooms", objectMapper.writeValueAsString(chatRoomCreateRequest))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        ChatRoomResponse resultResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ChatRoomResponse.class);

        assertThat(resultResponse).isNotNull();
        assertThat(resultResponse.getMessageResponses()).isNotNull();
        List<MessageResponse> messageResponses = resultResponse.getMessageResponses().getMessageResponses();
        assertAll(
            () -> assertEquals(resultResponse.getNickName(), "홍길동"),
            () -> assertEquals(messageResponses.get(0).getName(), "user0"),
            () -> assertEquals(messageResponses.get(1).getName(), "user1"),
            () -> assertEquals(messageResponses.get(2).getName(), "user2")
        );
    }
}