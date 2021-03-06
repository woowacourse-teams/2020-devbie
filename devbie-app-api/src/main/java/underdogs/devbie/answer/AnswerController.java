package underdogs.devbie.answer;

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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.dto.AnswerUpdateRequest;
import underdogs.devbie.answer.service.AnswerService;
import underdogs.devbie.auth.LoginUser;
import underdogs.devbie.auth.NoValidate;
import underdogs.devbie.auth.Role;
import underdogs.devbie.user.RoleType;
import underdogs.devbie.user.domain.User;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header")})
    @PostMapping
    public ResponseEntity<Void> save(@LoginUser User user, @RequestBody @Valid AnswerCreateRequest request) {
        Long id = answerService.save(user, request);
        return ResponseEntity
            .created(URI.create(String.format("/api/answers/%d", id)))
            .build();
    }

    @NoValidate
    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponse> read(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(answerService.read(id));
    }

    @NoValidate
    @GetMapping(params = "questionId")
    public ResponseEntity<AnswerResponses> readByQuestionId(@RequestParam(value = "questionId") Long questionId) {
        return ResponseEntity.ok(answerService.readByQuestionId(questionId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header")})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
        @LoginUser User user,
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody AnswerUpdateRequest request
    ) {
        answerService.update(user, id, request);

        return ResponseEntity
            .noContent()
            .build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devieToken", required = true, dataType = "String", paramType = "header")})
    @Role(role = {RoleType.ADMIN, RoleType.USER})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser User user, @PathVariable(value = "id") Long id) {
        answerService.delete(user, id);

        return ResponseEntity
            .noContent()
            .build();
    }
}
