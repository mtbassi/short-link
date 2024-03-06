package br.com.mtbassi.shortlink.infra.exceptionHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorCustomException extends RuntimeException{

    private final TypeExceptionEnum typeExceptionEnum;
}
