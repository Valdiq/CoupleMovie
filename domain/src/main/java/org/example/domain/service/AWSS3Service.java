package org.example.domain.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.example.logging.logger.ServiceLogger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AWSS3Service {

    private final AmazonS3 amazonS3Client;
    private final List<ServiceLogger> loggerList;

    public List<Bucket> getBucketList() {
        return amazonS3Client.listBuckets();
    }

    public String getObjectFromBucket(String bucketName, String objectName) {
        return amazonS3Client.getUrl(bucketName, objectName).toString();
    }

    public void putObjectInBucket(String bucketName, String objectName, String url) {
        objectName += ".jpg";
        File tmpFile = null;


        try {
            tmpFile = File.createTempFile("image", ".tmp");
            FileUtils.copyURLToFile(new URL(url), tmpFile);

            amazonS3Client.putObject(new PutObjectRequest(bucketName, objectName, tmpFile));

        } catch (AmazonServiceException | IOException e) {
            loggerList.forEach(
                    serviceLogger -> serviceLogger.log(
                            this.getClass().getName(),
                            "putObjectInBucket",
                            e.getClass().toString(),
                            "Error putting poster to S3 Storage: " + e.getMessage(),
                            LocalDateTime.now())
            );
        } finally {
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }


}
