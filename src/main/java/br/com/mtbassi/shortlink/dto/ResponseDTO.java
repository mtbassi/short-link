package br.com.mtbassi.shortlink.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDTO {

    private String shortLink;

    private String originalLink;

    private Integer access;

    private LocalDateTime creationDate;

}
