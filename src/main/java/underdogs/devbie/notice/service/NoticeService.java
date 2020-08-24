package underdogs.devbie.notice.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.domain.Notice;
import underdogs.devbie.notice.domain.NoticeRepository;
import underdogs.devbie.notice.dto.FilterResponses;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeReadRequest;
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
    @CachePut(value = "NoticeDetailResponses", key = "#id")
    @CacheEvict(value = "NoticeResponses", allEntries = true)
    public NoticeDetailResponse update(Long id, NoticeUpdateRequest request) {
        Notice notice = noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new);
        notice.update(request.toEntity(id));
        return NoticeDetailResponse.from(request.toEntity(id));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "NoticeResponses", allEntries = true),
        @CacheEvict(value = "NoticeDetailResponses", key = "#id")
    })
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }

    @Cacheable(value = "NoticeDetailResponses")
    public NoticeDetailResponse read(Long id) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(NoticeNotFoundException::new);
        return NoticeDetailResponse.from(notice);
    }

    @Cacheable(value = "NoticeResponses", keyGenerator = "noticeKeyGenerator")
    public NoticeResponses filteredRead(
        NoticeReadRequest noticeReadRequest, Pageable pageable
    ) {
        Page<Notice> noticePage = noticeRepository.findAllBy(
            noticeReadRequest.getNoticeType(),
            noticeReadRequest.getJobPosition(),
            noticeReadRequest.getLanguage(),
            noticeReadRequest.getKeyword(),
            pageable
        );
        return NoticeResponses.listFrom(noticePage.getContent(), noticePage.getTotalPages());
    }

    public FilterResponses findFilters() {
        return FilterResponses.get();
    }
}
