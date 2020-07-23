package underdogs.devbie.notice.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;

@WebMvcTest(controllers = NoticeController.class)
public class NoticeControllerTest extends MvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private NoticeService noticeService;
    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;
    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @DisplayName("시용자 요청을 받아 게시글 저장")
    @Test
    void save() throws Exception {
        NoticeCreateRequest noticeRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList("java", "javascript"))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();

        String inputJson = objectMapper.writeValueAsString(noticeRequest);

        given(noticeService.save(any(NoticeCreateRequest.class))).willReturn(1L);

        postAction("/api/notices", inputJson, "bearer token")
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 업데이트")
    @Test
    void update() throws Exception {
        NoticeUpdateRequest request = NoticeUpdateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList("java", "javascript"))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();

        String inputJson = objectMapper.writeValueAsString(request);

        doNothing().when(noticeService).update(anyLong(), any(NoticeUpdateRequest.class));

        putAction("/api/notices/1", inputJson, "bearer token")
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 삭제")
    @Test
    void delete() throws Exception {
        doNothing().when(noticeService).delete(anyLong());

        deleteAction("/api/notices/1", "bearer token")
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}
