package it.orbyta.fabrick.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FabrickApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;
    private final String description;

    public FabrickApiException(HttpStatus httpStatus, String code, String description) {
        super(description);
        this.httpStatus = httpStatus;
        this.code = code;
        this.description = description;
    }
}
