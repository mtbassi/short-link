package br.com.mtbassi.shortlink.infra.exceptionhandler;

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
    private final String method;
    private final String uri;
}
