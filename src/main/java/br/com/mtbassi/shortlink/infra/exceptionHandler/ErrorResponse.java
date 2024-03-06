package br.com.mtbassi.shortlink.infra.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private int status;
    private final String error;
    private final String message;
}
