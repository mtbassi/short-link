package br.com.mtbassi.shortlink.infra.exceptionhandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum TypeExceptionEnum {

    INVALID_URL_EXCEPTION(HttpStatus.BAD_REQUEST, "URL invalid.", "URL address must start with 'https://' or 'http://'"),
    ORIGINAL_LINK_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Original link not found.", "Parameter not found, try again."),
    GENERATE_SHORT_LINK_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating short link.", "Error generating short link, try again later."),
    GENERATE_QR_CODE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating qr code.", "Error generating qr code, try again later.");

    private final HttpStatus status;
    private final String error;
    private final String message;
}
