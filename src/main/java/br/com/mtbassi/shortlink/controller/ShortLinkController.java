package br.com.mtbassi.shortlink.controller;

import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.dto.ResponseDTO;
import br.com.mtbassi.shortlink.infra.exceptionhandler.ErrorResponse;
import br.com.mtbassi.shortlink.service.ShortLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/shortlink")
@RequiredArgsConstructor
@Tag(name = "Short link", description = "Contains operations for managing the short link.")
public class ShortLinkController {

    private final ShortLinkService service;

    @Operation(summary = "Short link.",
            description = "Feature creates a new short link.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid URL.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping
    public ResponseEntity<ResponseDTO> shortenLink(@RequestBody @Valid RequestDTO data, UriComponentsBuilder uriBuilder) {
        var response = service.shortenLink(data);
        var uri = uriBuilder.path("/shortlink/{id}").buildAndExpand(response.getShortLink()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<Void> redirect(@PathVariable String shortLink) {
        var originalLink = service.retrieveOriginalLink(shortLink);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, originalLink).build();
    }

    @GetMapping("/info/{shortLink}")
    public ResponseEntity<ResponseDTO> info(@PathVariable String shortLink) {
        return ResponseEntity.ok(service.info(shortLink));
    }

}
