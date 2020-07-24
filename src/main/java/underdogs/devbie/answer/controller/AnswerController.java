package underdogs.devbie.answer.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.service.AnswerService;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.user.domain.User;

@RestController
@RequestMapping("/api/answers")
@AllArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<Void> answer(@LoginUser User user, @RequestBody @Valid AnswerCreateRequest answerCreateRequest) {
        Long id = answerService.save(user, answerCreateRequest);

        return ResponseEntity.created(URI.create(String.format("/api/answers/%d", id)))
            .build();
    }
}
