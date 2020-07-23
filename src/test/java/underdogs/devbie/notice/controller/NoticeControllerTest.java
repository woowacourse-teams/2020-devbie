package underdogs.devbie.notice.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.NoticeDetail;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponse;
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

    private NoticeCreateRequest noticeCreateRequest;

    private NoticeUpdateRequest noticeUpdateRequest;

    @BeforeEach
    void setUp() {
        noticeCreateRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList("java", "javascript"))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();

        noticeUpdateRequest = NoticeUpdateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList("java", "javascript"))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();
        ;
    }

    @DisplayName("시용자 요청을 받아 게시글 저장")
    @Test
    void save() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeCreateRequest);

        given(noticeService.save(any(NoticeCreateRequest.class))).willReturn(1L);

        postAction("/api/notices", inputJson, "bearer token")
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 저장 예외 발생 - 유효하지 않은 이름")
    @Test
    void saveRequestWithInvalidName() throws Exception {
        noticeCreateRequest.setName("");
        validateNoticeCreateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 저장 예외 발생 - 유효하지 않은 연봉")
    @Test
    void saveRequestWithInvalidSalary() throws Exception {
        noticeCreateRequest.setSalary(0);
        validateNoticeCreateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 저장 예외 발생 - 유효하지 않은 프로그래밍 언어")
    @Test
    void saveRequestWithInvalidLanguages() throws Exception {
        noticeCreateRequest.setLanguages(Arrays.asList());
        validateNoticeCreateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 저장 예외 발생 - 유효하지 않은 회사 설명")
    @Test
    void saveRequestWithInvalidDescription() throws Exception {
        noticeCreateRequest.setDescription("");
        validateNoticeCreateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 업데이트")
    @Test
    void update() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeUpdateRequest);

        doNothing().when(noticeService).update(anyLong(), any(NoticeUpdateRequest.class));

        putAction("/api/notices/1", inputJson, "bearer token")
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 업데이트 예외 발생 - 유효하지 않은 이름")
    @Test
    void updateRequestWithInvalidName() throws Exception {
        noticeUpdateRequest.setName("");
        validateUpdateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 업데이트 예외 발생 - 유효하지 않은 연봉")
    @Test
    void updateRequestWithInvalidSalary() throws Exception {
        noticeUpdateRequest.setSalary(0);
        validateUpdateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 업데이트 예외 발생 - 유효하지 않은 프로그래밍 언어")
    @Test
    void updateRequestWithInvalidLanguages() throws Exception {
        noticeUpdateRequest.setLanguages(Arrays.asList());
        validateUpdateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 업데이트 예외 발생 - 유효하지 않은 설명")
    @Test
    void updateRequestWithInvalidDescription() throws Exception {
        noticeUpdateRequest.setDescription("");
        validateUpdateRequest();
    }

    @DisplayName("사용자 요청을 받아 게시글 삭제")
    @Test
    void delete() throws Exception {
        doNothing().when(noticeService).delete(anyLong());

        deleteAction("/api/notices/1", "bearer token")
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 전체 조회")
    @Test
    void readAll() throws Exception {
        List<NoticeResponse> noticeResponses = Arrays.asList(NoticeResponse.builder()
            .id(1L)
            .name("underdogs")
            .image("/static/image/underdogs")
            .jobPosition(JobPosition.BACKEND)
            .languages(new HashSet<>(Arrays.asList("java", "javascript")))
            .build());

        given(noticeService.readAll()).willReturn(noticeResponses);

        getAction("/api/notices")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id").value(1L))
            .andExpect(jsonPath("$.[0].name").value("underdogs"))
            .andExpect(jsonPath("$.[0].image").value("/static/image/underdogs"))
            .andExpect(jsonPath("$.[0].jobPosition").value("BACKEND"))
            .andExpect(jsonPath("$.[0].languages[0]").value("java"))
            .andExpect(jsonPath("$.[0].languages[1]").value("javascript"))
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 하나 조회")
    @Test
    void read() throws Exception {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        NoticeDetailResponse noticeDetailResponse = NoticeDetailResponse.builder()
            .id(1L)
            .company(new Company("bossdog", 60_000_000))
            .duration(new Duration(startDate, endDate))
            .jobPosition(JobPosition.FRONTEND)
            .image("/static/image/bossdog")
            .noticeDetail(new NoticeDetail(new HashSet<>(Arrays.asList("java", "javascript")), "You are hired!"))
            .build();

        given(noticeService.read(anyLong())).willReturn(noticeDetailResponse);

        getAction("/api/notices/1")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.company.name").value("bossdog"))
            .andExpect(jsonPath("$.company.salary").value(60_000_000))
            .andExpect(jsonPath("$.image").value("/static/image/bossdog"))
            .andExpect(jsonPath("$.jobPosition").value("FRONTEND"))
            .andExpect(jsonPath("$.noticeDetail.description").value("You are hired!"))
            .andExpect(jsonPath("$.noticeDetail.languages[0]").value("java"))
            .andExpect(jsonPath("$.noticeDetail.languages[1]").value("javascript"))
            .andDo(print());
    }

    private void validateNoticeCreateRequest() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeCreateRequest);
        given(noticeService.save(any(NoticeCreateRequest.class))).willReturn(1L);

        postAction("/api/notices", inputJson, "bearer token")
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    private void validateUpdateRequest() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeUpdateRequest);

        doNothing().when(noticeService).update(anyLong(), any(NoticeUpdateRequest.class));

        putAction("/api/notices/1", inputJson, "bearer token")
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }
}
