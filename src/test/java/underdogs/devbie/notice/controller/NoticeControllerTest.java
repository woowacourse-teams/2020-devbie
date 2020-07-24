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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeDescription;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDescriptionResponse;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;

@WebMvcTest(controllers = NoticeController.class)
public class NoticeControllerTest extends MvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
            .languages(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate(String.valueOf(LocalDateTime.now()))
            .endDate(String.valueOf(LocalDateTime.now()))
            .build();

        noticeUpdateRequest = NoticeUpdateRequest.builder()
            .name("underdogs")
            .salary(50_000_000)
            .languages(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName()))
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

        patchAction("/api/notices/1", inputJson, "bearer token")
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
            .languages(new HashSet<>(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName())))
            .build());

        given(noticeService.readAll()).willReturn(NoticeResponses.from(noticeResponses));

        getAction("/api/notices")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.noticeResponses[0].id").value(1L))
            .andExpect(jsonPath("$.noticeResponses[0].name").value("underdogs"))
            .andExpect(jsonPath("$.noticeResponses[0].image").value("/static/image/underdogs"))
            .andExpect(jsonPath("$.noticeResponses[0].jobPosition").value("BACKEND"))
            .andExpect(jsonPath("$.noticeResponses[0].languages[0]").value(Language.JAVA.getName()))
            .andExpect(jsonPath("$.noticeResponses[0].languages[1]").value(Language.JAVASCRIPT.getName()))
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
            .noticeDescription(
                new NoticeDescriptionResponse(
                    new NoticeDescription(
                        new HashSet<>(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName())),
                        "You are hired!")))
            .build();

        given(noticeService.read(anyLong())).willReturn(noticeDetailResponse);

        getAction("/api/notices/1")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.company.name").value("bossdog"))
            .andExpect(jsonPath("$.company.salary").value(60_000_000))
            .andExpect(jsonPath("$.image").value("/static/image/bossdog"))
            .andExpect(jsonPath("$.jobPosition").value("FRONTEND"))
            .andExpect(jsonPath("$.noticeDescription.content").value("You are hired!"))
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

        patchAction("/api/notices/1", inputJson, "bearer token")
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }
}
