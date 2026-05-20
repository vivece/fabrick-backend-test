package it.orbyta.fabrick.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
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

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<Problem> handleFabrickClientException(HttpStatusCodeException ex) {
        HttpStatus status = ex.getStatusCode();
        log.warn("Fabrick API returned an error. code={}, description={}", status.value(), ex.getResponseBodyAsString());
        Problem problem = buildProblem(status, ex.getResponseBodyAsString());
        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Problem> handleTimeout(ResourceAccessException ex) {
        Problem problem = buildProblem(HttpStatus.GATEWAY_TIMEOUT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(problem);
    }

    private Problem buildProblem(HttpStatus status, String errorMessage) {
        return Problem.builder()
                .withStatus(Status.valueOf(status.value()))
                .withDetail(errorMessage)
                .build();
    }
}
