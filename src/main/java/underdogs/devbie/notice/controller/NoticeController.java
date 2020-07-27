package underdogs.devbie.notice.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody NoticeCreateRequest request) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity
            .created(URI.create(String.format("/api/notices/%d", noticeId)))
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable Long id,
        @Valid @RequestBody NoticeUpdateRequest request
    ) {
        noticeService.update(id, request);
        return ResponseEntity
            .noContent()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping
    public ResponseEntity<NoticeResponses> readAll() {
        NoticeResponses responses = noticeService.readAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> readAll(@PathVariable Long id) {
        NoticeDetailResponse response = noticeService.read(id);
        return ResponseEntity.ok(response);
    }
}
