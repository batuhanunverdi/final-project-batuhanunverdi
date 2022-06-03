package org.patikadev.finalprojectbatuhanunverdi.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.exception.ConstraintViolationException;
import org.patikadev.finalprojectbatuhanunverdi.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@RestControllerAdvice
public class BaseControllerAdvice {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintViolationException(ConstraintViolationException constraintViolationException) {
        return ResponseEntity.badRequest().body(constraintViolationException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> onInvalidFormatException(InvalidFormatException invalidFormatException) {
        return ResponseEntity.badRequest().body(invalidFormatException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> onBaseExceptionHandled(BaseException baseException) {
        return ResponseEntity.badRequest().body(baseException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> onRuntimeExceptionHandled(RuntimeException baseException) {
        return ResponseEntity.badRequest().body(baseException.getMessage());
    }
}
