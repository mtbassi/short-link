package br.com.mtbassi.shortlink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestDTO {

    @NotBlank
    private String originalLink;
}
