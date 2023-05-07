package fr.stoodev.stoodo.post;

import fr.stoodev.stoodo.post.DTO.TopicCreationDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TopicService {
    Topic create(TopicCreationDTO topic);
    Optional<Topic> getOneByTopic(String topic);
    Optional<Topic> getOneById(Long topicId);
    Page<Topic> getList(int page, int size);
}
