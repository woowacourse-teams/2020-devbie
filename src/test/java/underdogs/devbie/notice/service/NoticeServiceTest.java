package underdogs.devbie.notice.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDescription;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.domain.NoticeType;
import underdogs.devbie.notice.dto.CustomPageRequest;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeReadRequest;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceTest {

    private NoticeService noticeService;

    @Mock
    private NoticeRepository noticeRepository;

    @BeforeEach
    void setUp() {
        noticeService = new NoticeService(noticeRepository);
    }

    @DisplayName("게시글 저장")
    @Test
    void save() {
        Set<Language> languages = Stream.of(Language.JAVA, Language.JAVASCRIPT)
            .collect(Collectors.toSet());
        Notice expected = Notice.builder()
            .id(1L)
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!"))
            .image("/static/image/underdogs")
            .duration(new Duration(LocalDateTime.of(2020, 10, 10, 14, 0),
                LocalDateTime.of(2020, 10, 10, 15, 0)))
            .build();
        given(noticeRepository.save(any(Notice.class))).willReturn(expected);

        NoticeCreateRequest noticeRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .salary(50_000_000)
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-10T14:00")
            .endDate("2020-10-10T15:00")
            .build();

        Long noticeId = noticeService.save(noticeRequest);

        assertThat(noticeId).isEqualTo(expected.getId());
    }

    @DisplayName("게시글 업데이트")
    @Test
    void update() {
        Long id = 2L;
        NoticeUpdateRequest request = NoticeUpdateRequest.builder()
            .name("underdogs")
            .title("우테코 모집")
            .noticeType(NoticeType.EDUCATION)
            .salary(50_000_000)
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-20T13:00")
            .endDate("2020-10-20T14:00")
            .build();

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(request.toEntity(2L)));

        noticeService.update(id, request);

        verify(noticeRepository).findById(eq(2L));
    }

    @DisplayName("게시글 삭제")
    @Test
    void delete() {
        doNothing().when(noticeRepository).deleteById(anyLong());

        noticeService.delete(1L);

        verify(noticeRepository).deleteById(eq(1L));
    }

    @DisplayName("필터된 게시글 조회")
    @Test
    void readAll() {
        Set<Language> languages = Stream.of(Language.JAVA, Language.JAVASCRIPT)
            .collect(Collectors.toSet());
        Notice expected = Notice.builder()
            .id(1L)
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!"))
            .image("/static/image/underdogs")
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        CustomPageRequest customPageRequest = new CustomPageRequest(1);
        NoticeReadRequest noticeReadRequest = NoticeReadRequest.builder()
            .noticeType(NoticeType.JOB)
            .jobPosition(JobPosition.BACKEND)
            .language(Language.JAVA)
            .keyword(Strings.EMPTY)
            .build();

        given(noticeRepository.findAllBy(any(NoticeType.class), any(JobPosition.class), any(Language.class),
            any(String.class), any(Pageable.class)))
            .willReturn(new PageImpl<>(Arrays.asList(expected)));

        List<NoticeResponse> noticeResponses = noticeService.filteredRead(
            noticeReadRequest,
            customPageRequest.toPageRequest()
        ).getNoticeResponses();

        assertAll(
            () -> assertThat(noticeResponses).isNotEmpty(),
            () -> assertThat(noticeResponses).hasSize(1),
            () -> assertThat(noticeResponses.get(0).getId()).isEqualTo(1L),
            () -> assertThat(noticeResponses.get(0).getName()).isEqualTo("underdogs"),
            () -> assertThat(noticeResponses.get(0).getImage()).isEqualTo("/static/image/underdogs"),
            () -> assertThat(noticeResponses.get(0).getLanguages()).contains(Language.JAVA.getText(),
                Language.JAVASCRIPT.getText()),
            () -> assertThat(noticeResponses.get(0).getJobPosition()).isEqualTo(JobPosition.BACKEND)
        );
    }

    @DisplayName("게시글 하나 조회")
    @Test
    void read() {
        Set<Language> languages = Stream.of(Language.JAVA, Language.JAVASCRIPT)
            .collect(Collectors.toSet());
        Notice expected = Notice.builder()
            .id(1L)
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!"))
            .image("/static/image/underdogs")
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(expected));

        NoticeDetailResponse noticeDetailResponse = noticeService.read(1L);

        assertAll(
            () -> assertThat(noticeDetailResponse.getId()).isEqualTo(1L),
            () -> assertThat(noticeDetailResponse.getCompany().getName()).isEqualTo("underdogs"),
            () -> assertThat(noticeDetailResponse.getCompany().getSalary()).isEqualTo(50_000_000),
            () -> assertThat(noticeDetailResponse.getImage()).isEqualTo("/static/image/underdogs"),
            () -> assertThat(noticeDetailResponse.getNoticeDescription().getLanguages()).contains(
                Language.JAVA.getText(), Language.JAVASCRIPT.getText()),
            () -> assertThat(noticeDetailResponse.getNoticeDescription().getContent()).isEqualTo("We are hiring!"),
            () -> assertThat(noticeDetailResponse.getJobPosition()).isEqualTo(JobPosition.BACKEND)
        );
    }
}
