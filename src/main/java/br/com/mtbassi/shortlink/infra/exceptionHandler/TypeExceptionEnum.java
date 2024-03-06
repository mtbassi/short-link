package br.com.mtbassi.shortlink.infra.exceptionHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum TypeExceptionEnum {

    INVALID_URL_EXCEPTION(HttpStatus.BAD_REQUEST, "URL invalid.", "URL address must start with 'https://' or 'http://'");

    private final HttpStatus status;
    private final String error;
    private final String message;
}
