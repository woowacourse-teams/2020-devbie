package underdogs.devbie.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.expception.NoticeNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(NoticeCreateRequest request) {
        Notice savedNotice = noticeRepository.save(request.toEntity());
        return savedNotice.getId();
    }

    @Transactional
    public void update(Long id, NoticeUpdateRequest request) {
        Notice notice = noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new);
        notice.update(request.toEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }

    public NoticeResponses readAll() {
        List<Notice> notices = noticeRepository.findAll();
        return NoticeResponses.listFrom(notices);
    }

    public NoticeDetailResponse read(Long id) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(NoticeNotFoundException::new);
        return NoticeDetailResponse.from(notice);
    }
}