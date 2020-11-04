package underdogs.devbie.notice.controller;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.resolver.LoginUserArgumentResolver;
import underdogs.devbie.notice.NoticeController;
import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDescription;
import underdogs.devbie.notice.domain.NoticeType;
import underdogs.devbie.notice.domain.RecruitmentType;
import underdogs.devbie.notice.dto.FilterResponses;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDescriptionResponse;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeReadRequest;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;
import underdogs.devbie.notice.vo.JobPositionPair;
import underdogs.devbie.notice.vo.LanguagePair;
import underdogs.devbie.s3.S3Service;

@WebMvcTest(controllers = NoticeController.class)
public class NoticeControllerTest extends MvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private NoticeService noticeService;

    @MockBean
    private S3Service s3Service;

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
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-10")
            .endDate("2020-10-20")
            .applyUrl("https://devbie.kr")
            .recruitmentType(RecruitmentType.OPEN)
            .build();

        noticeUpdateRequest = NoticeUpdateRequest.builder()
            .name("underdogs")
            .title("우테코 모집")
            .noticeType(NoticeType.EDUCATION)
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-20")
            .endDate("2020-10-20")
            .applyUrl("https://devbie.kr")
            .recruitmentType(RecruitmentType.OPEN)
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

    @DisplayName("사용자 요청을 받아 게시글 저장 예외 발생 - 유효하지 않은 프로그래밍 언어")
    @Test
    void saveRequestWithInvalidLanguages() throws Exception {
        noticeCreateRequest.setLanguages(new HashSet<>());
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

        given(noticeService.update(anyLong(), any(NoticeUpdateRequest.class)))
            .willReturn(NoticeDetailResponse.from(noticeUpdateRequest.toEntity(1L)));

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

    @DisplayName("사용자 요청을 받아 게시글 업데이트 예외 발생 - 유효하지 않은 프로그래밍 언어")
    @Test
    void updateRequestWithInvalidLanguages() throws Exception {
        noticeUpdateRequest.setLanguages(new HashSet<>());
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
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("underdogs"))
            .image("/static/image/underdogs")
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(
                Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet())
                , "hi", "https://devbie.kr"))
            .build());

        given(noticeService.filteredRead(any(NoticeReadRequest.class), any(Pageable.class)))
            .willReturn(NoticeResponses.listFrom(notices, 1000));

        MvcResult mvcResult = getAction("/api/notices?noticeType=JOB&page=1")
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
            () -> assertThat(noticeResponses.get(0).getLanguages()).contains(Language.JAVA.getText(),
                Language.JAVASCRIPT.getText())
        );
    }

    @DisplayName("사용자 요청을 받아 게시글 하나 조회")
    @Test
    void read() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        NoticeDetailResponse noticeDetailResponse = NoticeDetailResponse.builder()
            .id(1L)
            .company(new Company("bossdog"))
            .duration(new Duration(RecruitmentType.OPEN, startDate, endDate))
            .jobPosition(JobPosition.FRONTEND)
            .image("/static/image/bossdog")
            .noticeDescription(
                NoticeDescriptionResponse.from(
                    new NoticeDescription(
                        Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()),
                        "You are hired!", "https://devbie.kr")))
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
            () -> assertThat(noticeDetailResponse1.getImage()).isEqualTo("/static/image/bossdog"),
            () -> assertThat(noticeDetailResponse1.getJobPosition()).isEqualTo(JobPosition.FRONTEND),
            () -> assertThat(noticeDetailResponse1.getNoticeDescription().getContent()).isEqualTo("You are hired!"),
            () -> assertThat(noticeDetailResponse1.getNoticeDescription().getApplyUrl()).isEqualTo("https://devbie.kr")
        );
    }

    @DisplayName("사용자 요청을 통해 모든 필터정보 조회")
    @Test
    void findLanguages() throws Exception {
        given(noticeService.findFilters())
            .willReturn(FilterResponses.get());

        MvcResult mvcResult = getAction("/api/notices/filters")
            .andExpect(status().isOk())
            .andReturn();

        FilterResponses actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(),
            FilterResponses.class
        );

        List<LanguagePair> expectLanguages = Arrays.stream(Language.values())
            .map(LanguagePair::from)
            .collect(toList());
        List<JobPositionPair> expectJobPositions = Arrays.stream(JobPosition.values())
            .map(JobPositionPair::from)
            .collect(toList());

        assertAll(
            () -> assertThat(actual.getLanguages())
                .containsAll(expectLanguages),

            () -> assertThat(actual.getJobPositions())
                .containsAll(expectJobPositions)
        );
    }

    @DisplayName("공고 이미지 수정")
    @Test
    void updateImage() throws Exception {
        given(s3Service.upload(any())).willReturn("imagePath");

        ClassPathResource fileResource = new ClassPathResource(
            "/devbie.png");

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "image", fileResource.getFilename(),
            MediaType.MULTIPART_FORM_DATA_VALUE,
            fileResource.getInputStream());

        postAction("/api/notices/image", mockMultipartFile)
            .andExpect(status().isCreated())
            .andDo(print());
    }

    private void validateNoticeCreateRequest() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeCreateRequest);
        given(noticeService.save(any(NoticeCreateRequest.class))).willReturn(1L);

        postAction("/api/notices", inputJson, TEST_TOKEN)
            .andExpect(status().isMethodNotAllowed())
            .andDo(print());
    }

    private void validateUpdateRequest() throws Exception {
        String inputJson = objectMapper.writeValueAsString(noticeUpdateRequest);

        patchAction("/api/notices/1", inputJson, TEST_TOKEN)
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }
}
