package underdogs.devbie.notice.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.notice.dto.NoticeCreateRequest;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody NoticeCreateRequest request) {

        return ResponseEntity
            .created(URI.create("/api/notices/1"))
            .build();
    }
}
