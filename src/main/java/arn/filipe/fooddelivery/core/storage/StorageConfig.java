package arn.filipe.fooddelivery.core.storage;

import arn.filipe.fooddelivery.domain.service.PhotoStorageService;
import arn.filipe.fooddelivery.infrastructure.service.storage.LocalPhotoStorageService;
import arn.filipe.fooddelivery.infrastructure.service.storage.S3PhotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "fooddelivery.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3(){
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdAccessKey(),
                storageProperties.getS3().getSecretAccessKey());

        return AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withRegion(storageProperties.getS3().getRegion())
                        .build();
    }

    @Bean
    public PhotoStorageService photoStorageService(){
        if(StorageProperties.StorageType.S3.equals(storageProperties.getType())){
            return new S3PhotoStorageService();
        }
        else{
            return new LocalPhotoStorageService();
        }

    }
}
