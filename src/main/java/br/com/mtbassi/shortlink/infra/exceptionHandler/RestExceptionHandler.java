package br.com.mtbassi.shortlink.infra.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorCustomException.class)
    public ResponseEntity<Object> handleErrorCustomException(ErrorCustomException e){
        var response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(e.getTypeExceptionEnum().getStatus().value())
                .error(e.getTypeExceptionEnum().getError())
                .message(e.getTypeExceptionEnum().getMessage())
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
