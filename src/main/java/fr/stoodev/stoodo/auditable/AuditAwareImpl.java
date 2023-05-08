package fr.stoodev.stoodo.auditable;

import fr.stoodev.stoodo.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuditAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return Optional.empty();
        }

        var principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return Optional.of(user.getId());
        }

        return Optional.empty();
    }
}
