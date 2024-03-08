package br.com.mtbassi.shortlink.infra.scheduled;

import br.com.mtbassi.shortlink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledService {

    private final ShortLinkRepository shortLinkRepository;

    @Scheduled(initialDelay = 0, fixedRate = 24 * 60 * 60 * 1000)
    public void executeValidationOnApplicationLaunch(){
        deleteExpiredLinks();
    }

    @Scheduled(cron = "0 0,12 * * * *", zone = "America/Sao_Paulo")
    public void deleteExpiredLinks(){
        var cutOffDate = LocalDateTime.now().minusHours(24);
        shortLinkRepository.deleteAll(shortLinkRepository.findByCreationDateBefore(cutOffDate));
    }

}
