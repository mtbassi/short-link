package br.com.mtbassi.shortlink.stub;

import br.com.mtbassi.shortlink.domain.ShortLink;

import java.time.LocalDateTime;

public class ShortLinkStub {

    public static ShortLink valid() {
        return ShortLink.builder()
                .shortLink("MTBASSI")
                .originalLink("https://github.com/mtbassi")
                .access(0)
                .creationDate(LocalDateTime.of(2000, 8, 12, 0, 0, 0))
                .build();
    }
}
