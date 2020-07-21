package underdogs.devbie.notice.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.dto.NoticeCreateRequest;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long save(NoticeCreateRequest request) {
        Notice savedNotice = noticeRepository.save(request.toEntity());
        return savedNotice.getId();
    }
}
