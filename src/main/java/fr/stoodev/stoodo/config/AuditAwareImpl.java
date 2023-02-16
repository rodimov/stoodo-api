package fr.stoodev.stoodo.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        //User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(1L);
    }
}
