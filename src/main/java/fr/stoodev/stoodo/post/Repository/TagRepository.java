package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByTag(String tag);
}
