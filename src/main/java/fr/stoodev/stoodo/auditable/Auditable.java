package fr.stoodev.stoodo.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedBy
    @Column(updatable = false)
    protected Long createdBy;

    @CreatedDate
    @Column(updatable = false)
    protected Instant createdAt;

    @LastModifiedBy
    protected Long lastModifiedBy;

    @LastModifiedDate
    protected Instant lastModifiedAt;
}
