package com.itmagination.itmtest.common.error;

import com.itmagination.itmtest.security.InvalidCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    ResponseEntity<Object> handleLoginException(InvalidCredentialsException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
}
