package fr.stoodev.stoodo.post.Entity;

import fr.stoodev.stoodo.auditable.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts_content")
public class PostContent extends Auditable {
    @Id
    @GeneratedValue
    private UUID id;

    private Long version;

    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private boolean isCurrentVersion;
}
