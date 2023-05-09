package fr.stoodev.stoodo.image;

import fr.stoodev.stoodo.storage.S3BucketStorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final S3BucketStorageService storageService;
    private final ModelMapper modelMapper;

    public Optional<ImageDTO> upload(MultipartFile file) {
        Optional<String> extension = getExtensionByStringHandling(file.getOriginalFilename());

        if (extension.isEmpty() || !isSupportedExtension(extension.get())) {
            return Optional.empty();
        }

        Image image = Image.builder().build();
        image = imageRepository.save(image);

        URL url = storageService.uploadFile(image.getId().toString() + "." + extension.get(), file);

        image.setUrl(url.toString());
        image = imageRepository.save(image);

        return Optional.of(this.modelMapper.map(image, ImageDTO.class));
    }

    public Optional<ImageDTO> getById(UUID imageId) {
        return imageRepository.findById(imageId).map(image -> this.modelMapper.map(image, ImageDTO.class));
    }

    private Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private boolean isSupportedExtension(String extension) {
        return extension != null && (
                extension.equals("png")
                        || extension.equals("jpg")
                        || extension.equals("jpeg"));
    }
}
