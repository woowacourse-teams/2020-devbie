package underdogs.devbie.notice.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody NoticeCreateRequest request) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity
            .created(URI.create(String.format("/api/notices/%d", noticeId)))
            .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
        @RequestBody NoticeUpdateRequest noticeUpdateRequest) {
        noticeService.update(id, noticeUpdateRequest);
        return ResponseEntity
            .noContent()
            .build();
    }
}
