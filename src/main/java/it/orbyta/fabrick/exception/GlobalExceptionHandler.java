package it.orbyta.fabrick.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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

    private Problem buildProblem(HttpStatus status, String errorMessage) {
        return Problem.builder()
                .withStatus(Status.valueOf(status.value()))
                .withDetail(errorMessage)
                .build();
    }
}
