package fr.stoodev.stoodo.post.Entity;

import fr.stoodev.stoodo.auditable.Auditable;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts_content")
public class PostContent extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    private boolean isCurrentVersion;
}
