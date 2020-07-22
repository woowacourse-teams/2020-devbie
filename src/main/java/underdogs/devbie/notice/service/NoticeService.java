package underdogs.devbie.notice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long save(NoticeCreateRequest request) {
        Notice savedNotice = noticeRepository.save(request.toEntity());
        return savedNotice.getId();
    }

    @Transactional
    public void update(Long id, NoticeUpdateRequest noticeUpdateRequest) {
        Notice notice = noticeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        notice.update(noticeUpdateRequest.toEntity(id));
    }
}
