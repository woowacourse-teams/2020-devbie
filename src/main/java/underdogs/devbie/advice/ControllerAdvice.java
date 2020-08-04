package underdogs.devbie.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import underdogs.devbie.advice.dto.ErrorResponse;
import underdogs.devbie.exception.AlreadyExistException;
import underdogs.devbie.exception.CreateFailException;
import underdogs.devbie.exception.NotExistException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({NotExistException.class, CreateFailException.class, AlreadyExistException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(404)
            .message(exception.getMessage())
            .build();
        return ResponseEntity.badRequest()
            .body(errorResponse);
    }
}
