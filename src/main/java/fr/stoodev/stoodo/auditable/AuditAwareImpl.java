package fr.stoodev.stoodo.auditable;

import fr.stoodev.stoodo.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(1L);
    }
}
