package fr.stoodev.stoodo.post.DTO;

import fr.stoodev.stoodo.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUserHistoryDTO {
    private UUID id;
    private PostDTO post;
    private UserInfoDTO user;

    private boolean isOpened;
    private boolean isViewed;
    private boolean isLiked;
}
