package br.com.mtbassi.shortlink.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_links")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLink {

    @Id
    private String shortLink;

    private String originalLink;

    private Integer access;

    @CreationTimestamp
    private LocalDateTime creationDate;
}
