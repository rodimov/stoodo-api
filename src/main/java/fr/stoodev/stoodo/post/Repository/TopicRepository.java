package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTopic(String topic);
}
