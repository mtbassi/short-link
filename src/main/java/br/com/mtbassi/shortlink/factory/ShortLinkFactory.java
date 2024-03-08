package br.com.mtbassi.shortlink.factory;

import br.com.mtbassi.shortlink.domain.ShortLink;
import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.infra.exceptionhandler.ErrorCustomException;
import br.com.mtbassi.shortlink.infra.exceptionhandler.TypeExceptionEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortLinkFactory {

    public static ShortLink getInstance(RequestDTO data){
        var shortLink = generateShortLink(data.getOriginalLink());
        return ShortLink.builder()
                .shortLink(shortLink)
                .originalLink(data.getOriginalLink())
                .access(0)
                .build();
    }

    private static String generateShortLink(String originalLink){
        try {
            var input = originalLink.concat(String.valueOf(LocalDateTime.now()));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return Base64.getUrlEncoder().encodeToString(hash).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new ErrorCustomException(TypeExceptionEnum.GENERATE_SHORT_LINK_EXCEPTION, e.getMessage());
        }
    }

}
