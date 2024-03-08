package br.com.mtbassi.shortlink.repository;

import br.com.mtbassi.shortlink.domain.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {

    List<ShortLink> findByCreationDateBefore(LocalDateTime cutOffDate);

}
