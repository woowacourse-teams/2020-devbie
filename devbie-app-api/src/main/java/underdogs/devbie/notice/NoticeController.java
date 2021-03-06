package underdogs.devbie.notice;

import java.io.IOException;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.LoginUser;
import underdogs.devbie.auth.NoValidate;
import underdogs.devbie.auth.Role;
import underdogs.devbie.notice.dto.CustomPageRequest;
import underdogs.devbie.notice.dto.FilterResponses;
import underdogs.devbie.notice.dto.ImageUploadRequest;
import underdogs.devbie.notice.dto.NoticeCreateRequest;
import underdogs.devbie.notice.dto.NoticeDetailResponse;
import underdogs.devbie.notice.dto.NoticeReadRequest;
import underdogs.devbie.notice.dto.NoticeResponses;
import underdogs.devbie.notice.dto.NoticeUpdateRequest;
import underdogs.devbie.notice.service.NoticeService;
import underdogs.devbie.s3.S3Service;
import underdogs.devbie.user.RoleType;
import underdogs.devbie.user.domain.User;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final S3Service s3Service;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header", allowableValues = "ADMIN")})
    @Role(role = {RoleType.ADMIN})
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody NoticeCreateRequest request) {
        Long noticeId = noticeService.save(request);
        return ResponseEntity
            .created(URI.create(String.format("/api/notices/%d", noticeId)))
            .build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header", allowableValues = "ADMIN")})
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

    @PostMapping("/image")
    public ResponseEntity<Void> updateImage(
        @LoginUser User user,
        @Valid @ModelAttribute ImageUploadRequest request
    ) throws IOException {
        String imagePath = s3Service.upload(request.getImage());
        return ResponseEntity
            .created(URI.create(imagePath))
            .build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header", allowableValues = "ADMIN")})
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
        @Valid NoticeReadRequest noticeReadRequest,
        @Valid CustomPageRequest customPageRequest
    ) {
        NoticeResponses responses = noticeService.filteredRead(noticeReadRequest, customPageRequest.toPageRequest());
        return ResponseEntity.ok(responses);
    }

    @NoValidate
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> read(@PathVariable Long id) {
        NoticeDetailResponse response = noticeService.read(id);
        return ResponseEntity.ok(response);
    }

    @NoValidate
    @GetMapping("/filters")
    public ResponseEntity<FilterResponses> findLanguages() {
        FilterResponses filterResponse = noticeService.findFilters();
        return ResponseEntity.ok(filterResponse);
    }
}
