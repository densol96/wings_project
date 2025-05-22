package lv.wings.service.impl;

import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lv.wings.config.properties.AwsS3Properties;
import lv.wings.service.S3Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final AwsS3Properties properties;

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String key = folder + "/" + filename;

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

        return getPublicUrl(key);
    }

    @Override
    public void deleteFile(String url) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(extractKeyFromUrl(url))
                .build();

        s3Client.deleteObject(deleteRequest);
    }

    private String getPublicUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                properties.getBucket(), properties.getRegion(), key);
    }

    private String extractKeyFromUrl(String url) {
        String baseUrl = String.format("https://%s.s3.%s.amazonaws.com/", properties.getBucket(), properties.getRegion());
        return url.replace(baseUrl, "");
    }

}
