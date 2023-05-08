package fr.stoodev.stoodo.post.Entity;

import fr.stoodev.stoodo.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts_user_history")
public class PostUserHistory {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    private boolean isOpened;
    private boolean isViewed;
    private boolean isLiked;
}
