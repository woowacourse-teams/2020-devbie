package underdogs.devbie.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import lombok.extern.log4j.Log4j2;
import underdogs.devbie.advice.dto.ErrorResponse;
import underdogs.devbie.exception.BadRequestException;
import underdogs.devbie.exception.ForbiddenException;
import underdogs.devbie.exception.IntervalServerException;
import underdogs.devbie.exception.UnAuthorizedException;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(UnAuthorizedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.info("UnAuthorizedException : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.info("BadRequestException : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodNotAllowedException.class})
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.info("MethodArgumentNotValidException, MethodNotAllowedException : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(errorResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(ForbiddenException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.info("ForbiddenException : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(errorResponse);
    }

    @ExceptionHandler(IntervalServerException.class)
    public ResponseEntity<ErrorResponse> intervalServerExceptionHandler(IntervalServerException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.info("IntervalServerException : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandledExceptionHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        log.error("Exception : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }
}
