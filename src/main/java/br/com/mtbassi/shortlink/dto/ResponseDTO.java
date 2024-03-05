package br.com.mtbassi.shortlink.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ResponseDTO {

    private String shortLink;

    private String originalLink;

    private BigDecimal access;

    private LocalDate creationDate;

}
