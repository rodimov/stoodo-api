package fr.stoodev.stoodo.image;

import fr.stoodev.stoodo.auditable.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image extends Auditable {
    @Id
    @GeneratedValue
    private UUID id;
    private String url;
}
