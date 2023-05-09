package fr.stoodev.stoodo.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface ImageService {
    Optional<ImageDTO> upload(MultipartFile file);
    Optional<ImageDTO> getById(UUID imageId);
}
