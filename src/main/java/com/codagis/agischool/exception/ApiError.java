package com.codagis.agischool.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
    LocalDateTime timestamp,
    HttpStatus status,
    String message,
    String debugMessage,
    String path,
    Map<String, String> fieldErrors,
    List<String> globalErrors
) {
    public ApiError {
        fieldErrors = fieldErrors != null ? Collections.unmodifiableMap(fieldErrors) : Map.of();
        globalErrors = globalErrors != null ? Collections.unmodifiableList(globalErrors) : List.of();
    }

    public static ApiError of(HttpStatus status, String message, String path) {
        return new ApiError(
            LocalDateTime.now(),
            status,
            message,
            null,
            path,
            Map.of(),
            List.of()
        );
    }

    public static ApiError withDebug(HttpStatus status, String message, String debugMessage, String path) {
        return new ApiError(
            LocalDateTime.now(),
            status,
            message,
            debugMessage,
            path,
            Map.of(),
            List.of()
        );
    }

    public static ApiError withValidation(
        HttpStatus status,
        String message,
        String path,
        Map<String, String> fieldErrors,
        List<String> globalErrors
    ) {
        return new ApiError(
            LocalDateTime.now(),
            status,
            message,
            null,
            path,
            fieldErrors,
            globalErrors
        );
    }
}