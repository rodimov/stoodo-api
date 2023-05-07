package fr.stoodev.stoodo.post;

import fr.stoodev.stoodo.auditable.Auditable;
import fr.stoodev.stoodo.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String slug;
    @Column(name = "image_url")
    private String imageURL;
    private String description;
    private Boolean isPublished;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Topic> topics;
}
