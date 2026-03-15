package com.ata.job.controller;

import com.ata.job.constant.ErrorMapperConstants;
import com.ata.job.model.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Catches @Validated constraint violations (e.g. @DecimalMin, @Pattern, @Size)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleValidation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ErrorMapperConstants.MSG_VALIDATION_FAILED)
                .count(0)
                .data(errors)
                .build());
    }

    // Catches invalid sort field
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidSort(InvalidDataAccessApiUsageException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ErrorMapperConstants.INVALID_SORT_FIELD)
                .count(0)
                .data(List.of(ErrorMapperConstants.MSG_INVALID_SORT + ex.getMessage()))
                .build());
    }

    // Catches invalid fields parameter
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ErrorMapperConstants.INVALID_ARGUMENT)
                .count(0)
                .data(List.of(ex.getMessage()))
                .build());
    }

    // Catch-all for anything unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(ApiResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(ErrorMapperConstants.INTERNAL_SERVER_ERROR)
                .count(0)
                .data(List.of(ErrorMapperConstants.MSG_UNKNOWN_EXCEPTION))
                .build());
    }
}
