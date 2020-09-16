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
import underdogs.devbie.chat.domain.TitleColor;
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
        ChatRoomResponse chatRoomResponse = ChatRoomResponse.of(
            Arrays.asList(
                Chat.of("하늘하늘한 곰", TitleColor.AMBER, "message1", ChatRoom.from(noticeId)),
                Chat.of("찬란한 문어", TitleColor.BAROSSA, "message2", ChatRoom.from(noticeId)),
                Chat.of("어슴프레한 너구리", TitleColor.DARK_ORCHID, "message3", ChatRoom.from(noticeId))),
            3
        );

        given(chatService.fetchChatRoomInfo(any())).willReturn(chatRoomResponse);

        MvcResult mvcResult = patchAction(String.format("/api/chatrooms?noticeId=%s", noticeId), "")
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        ChatRoomResponse resultResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ChatRoomResponse.class);

        assertThat(resultResponse).isNotNull();
        assertThat(resultResponse.getMessageResponses()).isNotNull();
        List<MessageResponse> messageResponses = resultResponse.getMessageResponses().getMessageResponses();
        assertAll(
            () -> assertEquals(messageResponses.get(0).getName(), "하늘하늘한 곰"),
            () -> assertEquals(messageResponses.get(1).getName(), "찬란한 문어"),
            () -> assertEquals(messageResponses.get(2).getName(), "어슴프레한 너구리")
        );
    }
}
