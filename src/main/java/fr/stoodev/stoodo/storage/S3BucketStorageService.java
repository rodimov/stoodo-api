package fr.stoodev.stoodo.storage;

import java.net.URL;
import java.nio.file.Path;

public interface S3BucketStorageService {
    URL uploadFile(String keyName, Path fileLocation);
}
