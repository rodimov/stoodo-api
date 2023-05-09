package fr.stoodev.stoodo.storage;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface S3BucketStorageService {
    URL uploadFile(String keyName, MultipartFile file);
}
