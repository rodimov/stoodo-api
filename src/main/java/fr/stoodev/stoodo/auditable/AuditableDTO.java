package fr.stoodev.stoodo.auditable;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class AuditableDTO {
    protected UUID createdBy;
    protected Instant createdAt;
    protected UUID lastModifiedBy;
    protected Instant lastModifiedAt;
}
