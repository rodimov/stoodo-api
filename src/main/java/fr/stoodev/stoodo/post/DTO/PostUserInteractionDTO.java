package fr.stoodev.stoodo.post.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUserInteractionDTO {
    private boolean isOpened;
    private boolean isViewed;
    private boolean isLiked;
}
