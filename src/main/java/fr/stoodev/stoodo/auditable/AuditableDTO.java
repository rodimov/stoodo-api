package fr.stoodev.stoodo.auditable;

import lombok.Data;
import java.time.Instant;

@Data
public class AuditableDTO {
    protected Long createdBy;
    protected Instant createdAt;
    protected Long lastModifiedBy;
    protected Instant lastModifiedAt;
}
