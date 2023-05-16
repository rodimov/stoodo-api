package fr.stoodev.stoodo.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3BucketStorageServiceImpl implements S3BucketStorageService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${stoodo.aws.s3-bucket-name}")
    private String bucketName;

    public URL uploadFile(String keyName, Path fileLocation) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(Files.size(fileLocation));

            InputStream inputStream = Files.newInputStream(fileLocation);

            amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            inputStream.close();

            return amazonS3Client.getUrl(bucketName, keyName);
        } catch (AmazonClientException | IOException ioe) {
            return null;
        }
    }
}
