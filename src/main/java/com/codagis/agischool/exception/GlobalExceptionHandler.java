package com.codagis.agischool.exception;

import com.codagis.agischool.exception.exceptions.AutenticacaoException;
import com.codagis.agischool.exception.exceptions.EmailJaCadastradoException;
import com.codagis.agischool.exception.exceptions.MatriculaJaCadastradaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        List<String> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.withValidation(
                HttpStatus.BAD_REQUEST,
                "Erro de validação dos dados",
                request.getRequestURI(),
                fieldErrors,
                globalErrors
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler({AutenticacaoException.class, BadCredentialsException.class})
    public ResponseEntity<ApiError> handleAuthenticationException(
            RuntimeException ex, HttpServletRequest request) {

        ApiError apiError = ApiError.withDebug(
                HttpStatus.UNAUTHORIZED,
                "Falha na autenticação",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler({EmailJaCadastradoException.class, MatriculaJaCadastradaException.class})
    public ResponseEntity<ApiError> handleConflictExceptions(
            RuntimeException ex, HttpServletRequest request) {

        ApiError apiError = ApiError.of(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex, HttpServletRequest request) {

        ApiError apiError = ApiError.withDebug(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.internalServerError().body(apiError);
    }
}