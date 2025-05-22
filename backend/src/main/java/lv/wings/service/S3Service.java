package lv.wings.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file, String folder) throws IOException;

    void deleteFile(String url);
}
