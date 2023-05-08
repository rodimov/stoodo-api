package fr.stoodev.stoodo.post.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostStatisticsDTO {
    @JsonProperty("likes_count")
    private Long likesCount;

    @JsonProperty("opened_count")
    private Long openedCount;

    @JsonProperty("views_count")
    private Long viewsCount;
}
