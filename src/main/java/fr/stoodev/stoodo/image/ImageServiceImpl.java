package fr.stoodev.stoodo.image;

import fr.stoodev.stoodo.storage.S3BucketStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final S3BucketStorageService storageService;
    private final ModelMapper modelMapper;

    private final Path fileStorageLocation;

    @Value("${stoodo.files.max-image-resolution}")
    private int maxResolution;

    @Value("${stoodo.files.image-quality}")
    private float quality;

    @Autowired
    public ImageServiceImpl(@Value("${stoodo.files.uploads-dir}") String uploadsDir,
                            ImageRepository imageRepository,
                            S3BucketStorageService storageService,
                            ModelMapper modelMapper) {

        this.imageRepository = imageRepository;
        this.storageService = storageService;
        this.modelMapper = modelMapper;

        this.fileStorageLocation = Paths.get(uploadsDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    @Transactional
    public Optional<ImageDTO> upload(MultipartFile file) {
        Optional<String> extension = getExtensionByStringHandling(file.getOriginalFilename());

        if (extension.isEmpty() || !isSupportedExtension(extension.get())) {
            return Optional.empty();
        }

        Image image = Image.builder().build();
        image = imageRepository.save(image);

        Optional<String> filename = saveFile(file, image.getId().toString() + "." + extension.get());

        if (filename.isEmpty()) {
            return Optional.empty();
        }

        if (isImageNeedToBeConverted(extension.get())) {
            filename = convertImageToJPG(image.getId().toString(), extension.get());

            if (filename.isEmpty()) {
                removeFile(image.getId().toString() + "." + extension.get());
                return Optional.empty();
            }
        }

        filename = resizeImage(filename.get());

        if (filename.isEmpty()) {
            removeFile(image.getId().toString() + "." + extension.get());
            return Optional.empty();
        }

        Path fileLocation = this.fileStorageLocation.resolve(filename.get());

        URL url = storageService.uploadFile(filename.get(), fileLocation);

        removeFile(filename.get());

        image.setUrl(url.toString());
        image = imageRepository.save(image);

        return Optional.of(this.modelMapper.map(image, ImageDTO.class));
    }

    @Override
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
                        || extension.equals("jpeg")
                        || extension.equals("JPEG")
                        || extension.equals("JPG")
                        || extension.equals("wbmp")
                        || extension.equals("WBMP")
                        || extension.equals("PNG")
                        || extension.equals("gif")
                        || extension.equals("GIF")
                        || extension.equals("tif")
                        || extension.equals("TIF")
                        || extension.equals("tiff")
                        || extension.equals("TIFF")
                        || extension.equals("webp")
                        || extension.equals("WEBP")
        );
    }

    private boolean isImageNeedToBeConverted(String extension) {
        return extension == null || !extension.equals("jpg");
    }

    private Optional<String> saveFile(MultipartFile file, String filename) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return Optional.of(filename);
        } catch (IOException ex) {
            return Optional.empty();
        }
    }

    private Optional<String> convertImageToJPG(String filename, String extension) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(filename + "." + extension);

            InputStream fileInputStream = Files.newInputStream(targetLocation);
            BufferedImage image = ImageIO.read(fileInputStream);
            fileInputStream.close();

            if (image == null) {
                return Optional.empty();
            }

            BufferedImage convertedImage = new BufferedImage(image.getWidth(),
                    image.getHeight(), BufferedImage.TYPE_INT_RGB);

            convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            String outputFilename = filename + ".jpg";

            Path outputLocation = this.fileStorageLocation.resolve(outputFilename);

            OutputStream fileOutputStream = Files.newOutputStream(outputLocation);
            boolean canWrite = ImageIO.write(convertedImage, "jpg", fileOutputStream);
            fileOutputStream.close();

            Files.delete(targetLocation);

            if (!canWrite) {
                return Optional.empty();
            }

            return Optional.of(outputFilename);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<String> resizeImage(String filename, int scaledWidth, int scaledHeight) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(filename);

            InputStream fileInputStream = Files.newInputStream(targetLocation);
            BufferedImage inputImage = ImageIO.read(fileInputStream);
            fileInputStream.close();

            if (inputImage == null) {
                return Optional.empty();
            }

            BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());

            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

            if (!writers.hasNext()) {
                return Optional.empty();
            }

            ImageWriter writer = writers.next();

            OutputStream outputStream = Files.newOutputStream(targetLocation);
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            writer.write(null, new IIOImage(outputImage, null, null), param);

            outputStream.close();
            imageOutputStream.close();
            writer.dispose();

            return Optional.of(filename);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<String> resizeImage(String filename) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(filename);

            InputStream fileInputStream = Files.newInputStream(targetLocation);
            BufferedImage inputImage = ImageIO.read(fileInputStream);
            fileInputStream.close();

            if (inputImage == null) {
                return Optional.empty();
            }

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            double percent = 1;

            if (width > height) {
                if (width > maxResolution) {
                    percent = (double) maxResolution / width;
                }
            } else {
                if (height > maxResolution) {
                    percent = (double) maxResolution / height;
                }
            }

            int scaledWidth = (int) (inputImage.getWidth() * percent);
            int scaledHeight = (int) (inputImage.getHeight() * percent);

            return resizeImage(filename, scaledWidth, scaledHeight);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private boolean removeFile(String filename) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.delete(targetLocation);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
