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
