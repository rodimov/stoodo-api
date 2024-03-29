package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.TopicCreationDTO;
import fr.stoodev.stoodo.post.Entity.Topic;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface TopicService {
    Topic create(TopicCreationDTO topic);
    Optional<Topic> getOneByTopic(String topic);
    Optional<Topic> getOneById(UUID topicId);
    Page<Topic> getList(int page, int size);
}
