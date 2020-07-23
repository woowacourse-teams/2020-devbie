package underdogs.devbie.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(NoticeCreateRequest request) {
        Notice savedNotice = noticeRepository.save(request.toEntity());
        return savedNotice.getId();
    }

    @Transactional
    public void update(Long id, NoticeUpdateRequest noticeUpdateRequest) {
        Notice notice = noticeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        notice.update(noticeUpdateRequest.toEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }

    public List<NoticeResponse> readAll() {
        List<Notice> notices = noticeRepository.findAll();
        return NoticeResponse.toList(notices);
    }

    public NoticeDetailResponse read(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return NoticeDetailResponse.from(notice);
    }
}
