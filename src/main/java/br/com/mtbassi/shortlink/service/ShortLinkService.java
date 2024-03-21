package br.com.mtbassi.shortlink.service;

import br.com.mtbassi.shortlink.domain.ShortLink;
import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.dto.ResponseDTO;
import br.com.mtbassi.shortlink.factory.ShortLinkFactory;
import br.com.mtbassi.shortlink.infra.exceptionhandler.ErrorCustomException;
import br.com.mtbassi.shortlink.infra.exceptionhandler.TypeExceptionEnum;
import br.com.mtbassi.shortlink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ShortLinkService {

    private final ShortLinkRepository repository;

    private final QrCodeService qrCodeService;

    private final ModelMapper modelMapper;
    public ResponseDTO shortenLink(RequestDTO data){
        validateUrl(data.getOriginalLink().toLowerCase().trim());
        var shortLinkEntity = repository.save(ShortLinkFactory.getInstance(data));
        shortLinkEntity.setShortLink(getCompleteShortLink(shortLinkEntity.getShortLink()));
        return modelMapper.map(shortLinkEntity, ResponseDTO.class);
    }

    public byte[] shortenLinkQrCode(RequestDTO data){
        validateUrl(data.getOriginalLink().toLowerCase().trim());
        return qrCodeService.generateQrCode(data.getOriginalLink());
    }

    public String retrieveOriginalLink(String shortLink) {
        var shortLinkEntity = repository.findById(shortLink)
                .orElseThrow(() -> new ErrorCustomException(TypeExceptionEnum.ORIGINAL_LINK_NOT_FOUND_EXCEPTION));
        accountAccess(shortLinkEntity);
        repository.save(shortLinkEntity);
        return shortLinkEntity.getOriginalLink();
    }

    public ResponseDTO info(String shortLink){
        var shortLinkEntity = repository.findById(shortLink)
                .orElseThrow(() -> new ErrorCustomException(TypeExceptionEnum.ORIGINAL_LINK_NOT_FOUND_EXCEPTION));
        shortLinkEntity.setShortLink(getCompleteShortLink(shortLinkEntity.getShortLink()));
        return modelMapper.map(shortLinkEntity, ResponseDTO.class);
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

    private void validateUrl(String url){
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            throw new ErrorCustomException(TypeExceptionEnum.INVALID_URL_EXCEPTION);
        }
    }
}
