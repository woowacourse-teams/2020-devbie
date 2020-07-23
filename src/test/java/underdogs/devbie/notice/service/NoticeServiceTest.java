package underdogs.devbie.notice.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.notice.domain.Company;
import underdogs.devbie.notice.domain.Duration;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeDetail;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
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
        Set<String> languages = Stream.of("java", "javascript")
            .collect(Collectors.toSet());
        Notice expected = Notice.builder()
            .id(1L)
            .company(new Company("underdogs", 50_000_000))
            .jobPosition(JobPosition.BACKEND)
            .noticeDetail(new NoticeDetail(languages, "We are hiring!"))
            .image("/static/image/underdogs")
            .duration(new Duration(LocalDateTime.now(), LocalDateTime.now()))
            .build();
        given(noticeRepository.save(any(Notice.class))).willReturn(expected);

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

        Long noticeId = noticeService.save(noticeRequest);

        assertThat(noticeId).isEqualTo(expected.getId());
    }

    @DisplayName("게시글 업데이트")
    @Test
    void update() {
        Long id = 2L;
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
}
