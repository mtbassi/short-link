package br.com.mtbassi.shortlink.controller;

import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.dto.ResponseDTO;
import br.com.mtbassi.shortlink.service.ShortLinkService;
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
public class ShortLinkController {

    private final ShortLinkService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> shortenLink(@RequestBody @Valid RequestDTO data, UriComponentsBuilder uriBuilder){
        var response = service.shortenLink(data);
        var uri = uriBuilder.path("/shortlink/{id}").buildAndExpand(response.getShortLink()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<Void> redirect(@PathVariable String shortLink){
        var originalLink = service.retrieveOriginalLink(shortLink);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, originalLink).build();
    }

}
