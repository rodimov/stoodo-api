package fr.stoodev.stoodo.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class S3BucketStorageServiceImpl implements S3BucketStorageService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${stoodo.aws.s3-bucket-name}")
    private String bucketName;

    public URL uploadFile(String keyName, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3Client.getUrl(bucketName, keyName);
        } catch (IOException ioe) {
            return null;
        } catch (AmazonServiceException serviceException) {
            return null;
        } catch (AmazonClientException clientException) {
            return null;
        }
    }
}
