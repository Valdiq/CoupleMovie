package org.example.domain.configuration;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.example.domain.properties.AWSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AWSS3Configuration {

    private final AWSProperties awsProperties;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(awsProperties.accessKey(), awsProperties.secretKey());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }

}
