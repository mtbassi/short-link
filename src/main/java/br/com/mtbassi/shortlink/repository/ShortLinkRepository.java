package br.com.mtbassi.shortlink.repository;

import br.com.mtbassi.shortlink.domain.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {
}
