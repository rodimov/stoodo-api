package fr.stoodev.stoodo.post.Entity;

import fr.stoodev.stoodo.auditable.Auditable;
import fr.stoodev.stoodo.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post extends Auditable {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String slug;

    @Column(name = "image_url")
    private String imageURL;

    private String description;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;
}
