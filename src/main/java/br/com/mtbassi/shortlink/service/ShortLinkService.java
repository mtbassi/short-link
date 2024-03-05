package br.com.mtbassi.shortlink.service;

import br.com.mtbassi.shortlink.domain.ShortLink;
import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.dto.ResponseDTO;
import br.com.mtbassi.shortlink.factory.ShortLinkFactory;
import br.com.mtbassi.shortlink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ShortLinkService {

    private static String BASE_URL = "http://localhost:8080/shortlink/";

    private final ShortLinkRepository repository;

    private final ModelMapper modelMapper;
    public ResponseDTO shortenLink(RequestDTO data){
        var shortLinkEntity = repository.save(ShortLinkFactory.getInstance(data));
        shortLinkEntity.setShortLink(getCompleteShortLink(shortLinkEntity.getShortLink()));
        return modelMapper.map(shortLinkEntity, ResponseDTO.class);
    }

    public String retrieveOriginalLink(String shortLink) {
        ShortLink object = repository.findById(shortLink).orElseThrow(RuntimeException::new);
        accountAccess(object);
        repository.save(object);
        return object.getOriginalLink();
    }

    private void accountAccess(ShortLink shortLink){
        shortLink.setAccess(shortLink.getAccess() + 1);
    }

    private String getCompleteShortLink(String shortLink){
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/shortlink/{shortLink}")
                .buildAndExpand(shortLink)
                .toUriString();
    }
}
