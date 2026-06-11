package com.agent.ai_interview_coach.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeminiApiException.class)
    public ResponseEntity<ErrorResponse> handleGeminiApiException(
            GeminiApiException ex, HttpServletRequest request) {

        log.error("Gemini API error on [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(buildError(HttpStatus.BAD_GATEWAY, "Gemini API Error", ex.getMessage(), request));
    }

    @ExceptionHandler(ResponseParseException.class)
    public ResponseEntity<ErrorResponse> handleResponseParseException(
            ResponseParseException ex, HttpServletRequest request) {

        log.error("Response parse error on [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Parse Error", ex.getMessage(), request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (existing, replacement) -> existing));

        log.warn("Validation failed on [{}]: {}", request.getRequestURI(), fieldErrors);

        ErrorResponse body = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Request contains invalid fields")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .validationErrors(fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    // ── Phase 2 exceptions ────────────────────────────────────────────────────

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        log.warn("Resource not found on [{}]: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request));
    }

    @ExceptionHandler(GenericApplicationException.class)
    public ResponseEntity<ErrorResponse> handleGenericApplicationException(
            GenericApplicationException ex, HttpServletRequest request) {

        log.error("Application error on [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Application Error", ex.getMessage(), request));
    }

    // ── Catch-all (must remain last) ─────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected error on [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                        "An unexpected error occurred. Please try again later.", request));
    }

    private ErrorResponse buildError(HttpStatus status, String error, String message,
                                     HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
