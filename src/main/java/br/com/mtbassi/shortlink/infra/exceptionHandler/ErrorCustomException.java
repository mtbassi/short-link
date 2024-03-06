package br.com.mtbassi.shortlink.infra.exceptionHandler;

import lombok.Getter;

@Getter
public class ErrorCustomException extends RuntimeException{

    private final TypeExceptionEnum typeExceptionEnum;

    public ErrorCustomException(TypeExceptionEnum typeExceptionEnum) {
        super(typeExceptionEnum.getMessage());
        this.typeExceptionEnum = typeExceptionEnum;
    }

    public ErrorCustomException(TypeExceptionEnum typeExceptionEnum, String message) {
        super(message);
        this.typeExceptionEnum = typeExceptionEnum;
    }
}
