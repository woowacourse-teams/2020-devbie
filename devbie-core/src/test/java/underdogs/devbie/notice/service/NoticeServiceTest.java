package underdogs.devbie.notice.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
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
import underdogs.devbie.notice.domain.RecruitmentType;
import underdogs.devbie.notice.dto.CustomPageRequest;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeReadRequest;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.expception.NoticeNotFoundException;

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
            .company(new Company("underdogs"))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!", "https://devbie.kr"))
            .image("/static/image/underdogs")
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.of(2020, 10, 10),
                LocalDate.of(2020, 10, 10)))
            .build();
        given(noticeRepository.save(any(Notice.class))).willReturn(expected);

        NoticeCreateRequest noticeRequest = NoticeCreateRequest.builder()
            .name("underdogs")
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-10")
            .endDate("2020-10-10")
            .applyUrl("https://devbie.kr")
            .recruitmentType(RecruitmentType.OPEN)
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
            .languages(Stream.of(Language.JAVA, Language.JAVASCRIPT).collect(Collectors.toSet()))
            .jobPosition(JobPosition.BACKEND)
            .image("/static/image/underdogs")
            .description("We are hiring!")
            .startDate("2020-10-20")
            .endDate("2020-10-20")
            .applyUrl("https://devbie.kr")
            .recruitmentType(RecruitmentType.OPEN)
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
            .company(new Company("underdogs"))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!", "https://devbie.kr"))
            .image("/static/image/underdogs")
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
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
            .company(new Company("underdogs"))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!", "https://devbie.kr"))
            .image("/static/image/underdogs")
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
            .build();

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(expected));

        NoticeDetailResponse noticeDetailResponse = noticeService.read(1L);

        assertAll(
            () -> assertThat(noticeDetailResponse.getId()).isEqualTo(1L),
            () -> assertThat(noticeDetailResponse.getCompany().getName()).isEqualTo("underdogs"),
            () -> assertThat(noticeDetailResponse.getImage()).isEqualTo("/static/image/underdogs"),
            () -> assertThat(noticeDetailResponse.getNoticeDescription().getLanguages()).contains(
                Language.JAVA.getText(), Language.JAVASCRIPT.getText()),
            () -> assertThat(noticeDetailResponse.getNoticeDescription().getContent()).isEqualTo("We are hiring!"),
            () -> assertThat(noticeDetailResponse.getJobPosition()).isEqualTo(JobPosition.BACKEND),
            () -> assertThat(noticeDetailResponse.getNoticeDescription().getApplyUrl()).isEqualTo("https://devbie.kr")
        );
    }

    @DisplayName("없는 공고 조회 시 예외 발생")
    @Test
    void read_Invalid_Notice_Should_Throw_NoticeNotFoundException() {

        given(noticeRepository.findById(anyLong())).willThrow(new NoticeNotFoundException());

        assertThatThrownBy(() -> noticeService.read(anyLong()))
            .isInstanceOf(NoticeNotFoundException.class)
            .hasMessageContaining("해당 공고를 찾을 수 없습니다.");
    }

    @DisplayName("공고 아이디로 공고 조회")
    @Test
    void findAllByIds() {
        Set<Language> languages = Stream.of(Language.JAVA, Language.JAVASCRIPT)
            .collect(Collectors.toSet());

        Notice notice1 = Notice.builder()
            .id(1L)
            .title("언더독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("underdogs"))
            .jobPosition(JobPosition.BACKEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!", "https://devbie.kr"))
            .image("/static/image/underdogs")
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
            .build();

        Notice notice2 = Notice.builder()
            .id(2L)
            .title("보스독스 채용")
            .noticeType(NoticeType.JOB)
            .company(new Company("bossdogs"))
            .jobPosition(JobPosition.FRONTEND)
            .noticeDescription(new NoticeDescription(languages, "We are hiring!", "https://devbie.kr"))
            .image("/static/image/underdogs")
            .duration(new Duration(RecruitmentType.OPEN, LocalDate.now(), LocalDate.now()))
            .build();

        List<Notice> notices = Lists.newArrayList(notice1, notice2);

        given(noticeRepository.findAllById(anyList())).willReturn(notices);

        NoticeResponses responses = noticeService.findAllByIds(Lists.newArrayList(1L, 2L));

        assertAll(
            () -> assertThat(responses.getNoticeResponses().get(0).getTitle()).isEqualTo("언더독스 채용"),
            () -> assertThat(responses.getNoticeResponses().get(1).getTitle()).isEqualTo("보스독스 채용")
        );
    }
}
