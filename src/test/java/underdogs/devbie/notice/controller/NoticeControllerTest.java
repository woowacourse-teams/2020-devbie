package underdogs.devbie.notice.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.Notice;
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
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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

        postAction("/api/notices", inputJson, TEST_TOKEN)
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

        patchAction("/api/notices/1", inputJson, TEST_TOKEN)
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
        willDoNothing().given(noticeService).delete(anyLong());

        deleteAction("/api/notices/1", TEST_TOKEN)
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("사용자 요청을 받아 게시글 전체 조회")
    @Test
    void readAll() throws Exception {
        List<Notice> notices = Arrays.asList(Notice.builder()
            .id(1L)
            .company(new Company("underdogs", 2000))
            .image("/static/image/underdogs")
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(
                new HashSet<>(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName()))
                , "hi"))
            .build());

        given(noticeService.readAll()).willReturn(NoticeResponses.listFrom(notices));

        MvcResult mvcResult = getAction("/api/notices")
            .andExpect(status().isOk())
            .andReturn();

        List<NoticeResponse> noticeResponses = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            NoticeResponses.class)
            .getNoticeResponses();

        assertAll(
            () -> assertThat(noticeResponses.get(0).getId()).isEqualTo(1L),
            () -> assertThat(noticeResponses.get(0).getName()).isEqualTo("underdogs"),
            () -> assertThat(noticeResponses.get(0).getImage()).isEqualTo("/static/image/underdogs"),
            () -> assertThat(noticeResponses.get(0).getJobPosition()).isEqualTo(JobPosition.BACKEND),
            () -> assertThat(noticeResponses.get(0).getLanguages()).contains(Language.JAVA.getName(),
                Language.JAVASCRIPT.getName())
        );
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
                NoticeDescriptionResponse.from(
                    new NoticeDescription(
                        new HashSet<>(Arrays.asList(Language.JAVA.getName(), Language.JAVASCRIPT.getName())),
                        "You are hired!")))
            .build();

        given(noticeService.read(anyLong())).willReturn(noticeDetailResponse);

        MvcResult mvcResult = getAction("/api/notices/1")
            .andExpect(status().isOk())
            .andReturn();

        NoticeDetailResponse noticeDetailResponse1 = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(),
            NoticeDetailResponse.class);

        assertAll(
            () -> assertThat(noticeDetailResponse1.getId()).isEqualTo(1L),
            () -> assertThat(noticeDetailResponse1.getCompany().getName()).isEqualTo("bossdog"),
            () -> assertThat(noticeDetailResponse1.getCompany().getSalary()).isEqualTo(60_000_000),
            () -> assertThat(noticeDetailResponse1.getImage()).isEqualTo("/static/image/bossdog"),
            () -> assertThat(noticeDetailResponse1.getJobPosition()).isEqualTo(JobPosition.FRONTEND),
            () -> assertThat(noticeDetailResponse1.getNoticeDescription().getContent()).isEqualTo("You are hired!")
        );
    }

    private void validateNoticeCreateRequest() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeCreateRequest);
        given(noticeService.save(any(NoticeCreateRequest.class))).willReturn(1L);

        postAction("/api/notices", inputJson, TEST_TOKEN)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    private void validateUpdateRequest() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeUpdateRequest);

        willDoNothing().given(noticeService).update(anyLong(), any(NoticeUpdateRequest.class));

        patchAction("/api/notices/1", inputJson, TEST_TOKEN)
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }
}
