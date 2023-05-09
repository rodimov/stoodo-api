package fr.stoodev.stoodo.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@Tag(name = "Image", description = "The Image API. Contains all the operations that can be performed on a image.")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Transactional
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image", description = "Upload image")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ImageDTO> upload(@RequestParam("file") MultipartFile file) {
        Optional<ImageDTO> imageDTO = imageService.upload(file);

        return imageDTO.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/get_by_id/{id}")
    @Operation(summary = "Get image", description = "Return image by id")
    public ResponseEntity<ImageDTO> getOneById(@PathVariable("id") UUID imageId) {
        Optional<ImageDTO> imageDTO = imageService.getById(imageId);

        return imageDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }
}
