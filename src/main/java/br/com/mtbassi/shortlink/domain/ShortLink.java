package br.com.mtbassi.shortlink.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "short_links")
@Getter
@Setter
public class ShortLink {

    @Id
    private String shortLink;

    private String originalLink;

    private BigDecimal access;

    @CreationTimestamp
    private LocalDate creationDate;
}
