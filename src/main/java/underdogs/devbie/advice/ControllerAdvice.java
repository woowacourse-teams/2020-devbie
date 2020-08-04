package underdogs.devbie.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import underdogs.devbie.advice.dto.ErrorResponse;
import underdogs.devbie.auth.exception.InvalidAuthenticationException;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.exception.CreateFailException;
import underdogs.devbie.exception.NotExistException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({NotExistException.class
        , CreateFailException.class
        , AlreadyExistException.class,
        InvalidAuthenticationException.class})
    public ResponseEntity<ErrorResponse> handledExceptionHandler(RuntimeException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(404)
            .message(exception.getMessage())
            .build();
        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> missedExceptionHandler(RuntimeException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(500)
            .message(exception.getMessage())
            .build();
        return ResponseEntity.badRequest()
            .body(errorResponse);
    }
}
