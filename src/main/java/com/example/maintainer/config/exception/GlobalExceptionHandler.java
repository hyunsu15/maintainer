package com.example.maintainer.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  public static final String UNEXPECTED_ERROR_MESSAGE = "예상치 못한 에러입니다.";

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> expectedError(BaseException baseException) {
    log.info("exception", baseException);
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(
            new ErrorMeta(HttpStatus.BAD_REQUEST.value(), baseException.getMessage())));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> unExpectedError(Exception exception) {
    log.info("new Exception, not expected Exception", exception);
    return ResponseEntity.internalServerError()
        .body(new ErrorResponse(
            new ErrorMeta(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_ERROR_MESSAGE)));
  }
}
