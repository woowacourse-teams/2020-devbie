package underdogs.devbie.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import underdogs.devbie.advice.dto.ErrorResponse;
import underdogs.devbie.exception.UnAuthorizedException;

@RestControllerAdvice
public class ControllerAdvice {

    // 401 - Unauthorized 해당 요청을 보내는 주체의 신분 확인이 요구되거나 확인할 수 없었을 때 보내는 응답 코드 => 로그인이 필요한 경우
    // AccessTokenLoadException

    // 403 - forbidden(권한 없음)

    // 404 - Not Found 요청받은 리소스를 찾을 수 없을

    // 409 - conflict 리소스의 현재 상태와 충돌해서 요청을 처리할 수 없으므로 클라이언트가 요청을 충돌을 수정해서 요청을 다시 보내야되는 경우
    // 예를 들자면, 회원가입 창에서 아이디의 중복을 거를 때 아이디가 존재하는지 확인하는 요청에는 409를 응답해야한다.

    // 500 - 서버가 예상하지 못한 에러가 발생했다.

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(exception.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(errorResponse);
    }
    // @ExceptionHandler({NotExistException.class
    //     , CreateFailException.class
    //     , AlreadyExistException.class})
    // public ResponseEntity<ErrorResponse> handledExceptionHandler(RuntimeException exception) {
    //     ErrorResponse errorResponse = ErrorResponse.builder()
    //         .statusCode(404)
    //         .message(exception.getMessage())
    //         .build();
    //     return ResponseEntity.badRequest()
    //         .body(errorResponse);
    // }
    //
    // @ExceptionHandler(RuntimeException.class)
    // public ResponseEntity<ErrorResponse> missedExceptionHandler(RuntimeException exception) {
    //     ErrorResponse errorResponse = ErrorResponse.builder()
    //         .statusCode(500)
    //         .message(exception.getMessage())
    //         .build();
    //     return ResponseEntity.badRequest()
    //         .body(errorResponse);
    // }
}
