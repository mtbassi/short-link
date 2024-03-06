package br.com.mtbassi.shortlink.infra.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorCustomException.class)
    public ResponseEntity<Object> handleErrorCustomException(ErrorCustomException e){
        var response = buildErrorResponse(e);
        log.warn(response.getError(), e);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private ErrorResponse buildErrorResponse(ErrorCustomException e) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(e.getTypeExceptionEnum().getStatus().value())
                .error(e.getTypeExceptionEnum().getError())
                .message(e.getTypeExceptionEnum().getMessage())
                .build();
    }

}
