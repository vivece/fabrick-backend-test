package it.orbyta.fabrick.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidationException(MethodArgumentNotValidException ex) {
        String detailErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        Problem problem = Problem.builder()
                .withStatus(Status.BAD_REQUEST)
                .withDetail(detailErrorMessage)
                .build();

        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        Problem problem = buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(FabrickApiException.class)
    public ResponseEntity<Problem> handleFabrickApiException(FabrickApiException ex) {
        log.warn("Fabrick API returned an error. code={}, description={}", ex.getCode(), ex.getDescription());
        HttpStatus status = ex.getHttpStatus();
        if (status == null || status.is5xxServerError()) {
            status = HttpStatus.BAD_GATEWAY;
        }
        Problem problem = buildProblem(status, ex.getDescription());
        return ResponseEntity.status(status).body(problem);
    }
/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        Problem problem = buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation error", detail, "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Problem> handleConstraintViolation(ConstraintViolationException ex) {
        Problem problem = buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation error", ex.getMessage(), "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Problem> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String detail = ex.getParameterName() + ": required request parameter is missing";
        Problem problem = buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation error", detail, "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Problem> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String detail = ex.getName() + ": invalid value";
        Problem problem = buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation error", detail, "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Problem> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Problem problem = buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation error", "Request body is missing or malformed", "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Problem> handleRestClientException(RestClientException ex) {
        log.error("Error while calling Fabrick API", ex);
        Problem problem = buildProblem(HttpStatus.BAD_GATEWAY, "Fabrick communication error", ex.getMessage(), "FABRICK_COMMUNICATION_ERROR");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(problem);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }*/

    private Problem buildProblem(HttpStatus status, String errorMessage) {
        return Problem.builder()
                .withStatus(Status.valueOf(status.value()))
                .withDetail(errorMessage)
                .build();
    }
}
