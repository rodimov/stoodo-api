package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    Optional<Topic> findByTopic(String topic);
}
