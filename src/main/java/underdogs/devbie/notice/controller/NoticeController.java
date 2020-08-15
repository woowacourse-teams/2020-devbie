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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.interceptor.annotation.Role;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeType;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;
import underdogs.devbie.notice.vo.JobPositionsResponse;
import underdogs.devbie.notice.vo.LanguagesResponse;
import underdogs.devbie.user.domain.RoleType;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Role(role = {RoleType.ADMIN})
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody NoticeCreateRequest request) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity
            .created(URI.create(String.format("/api/notices/%d", noticeId)))
            .build();
    }

    @Role(role = {RoleType.ADMIN})
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

    @Role(role = {RoleType.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @NoValidate
    @GetMapping
    public ResponseEntity<NoticeResponses> readAll(
        @RequestParam NoticeType noticeType,
        @RequestParam(required = false) JobPosition jobPosition,
        @RequestParam(required = false) Language language
    ) {
        NoticeResponses responses = noticeService.filteredRead(noticeType, jobPosition, language);
        return ResponseEntity.ok(responses);
    }

    @NoValidate
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> read(@PathVariable Long id) {
        NoticeDetailResponse response = noticeService.read(id);
        return ResponseEntity.ok(response);
    }

    @NoValidate
    @GetMapping("/languages")
    public ResponseEntity<LanguagesResponse> findLanguages() {
        LanguagesResponse languageResponses = noticeService.findLanguages();
        return ResponseEntity.ok(languageResponses);
    }

    @NoValidate
    @GetMapping("/job-positions")
    public ResponseEntity<JobPositionsResponse> findJobPositions() {
        JobPositionsResponse jobPositionsResponse = noticeService.findJobPositions();
        return ResponseEntity.ok(jobPositionsResponse);
    }
}
