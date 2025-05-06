package lv.wings.controller.open;

import java.io.File;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import lv.wings.responses.ApiResponse;

@Controller
@RequestMapping
public class ImagesController {

    @Value("${upload.directory.events}")
    private String uploadEventsDir;

    @Value("${upload.directory.products}")
    private String uploadProductsDir;


    // @GetMapping("images/{picturePath}")
    // public ResponseEntity<?> getEventImage(@PathVariable String picturePath){

    // try {
    // File imageFile1 = new File(uploadEventsDir + "/" + picturePath);
    // File imageFile2 = new File(uploadProductsDir + "/" + picturePath);



    // if (!imageFile1.exists() && !imageFile2.exists()){
    // ApiResponse<String> notFoundResponse = new ApiResponse<>("No picture found!", null);
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    // }

    // File existingFile = imageFile1.exists() ? imageFile1 : imageFile2;

    // FileSystemResource fileSystemResource = new FileSystemResource(existingFile);

    // String contentType = URLConnection.guessContentTypeFromName(existingFile.getName());
    // if (contentType == null) {
    // contentType = "application/octet-stream";
    // }

    // return ResponseEntity.ok()
    // .contentType(MediaType.valueOf(contentType))
    // .body(fileSystemResource);

    // } catch (Exception e) {
    // return ResponseEntity.internalServerError().body(
    // new ApiResponse<>(e.getMessage(), null)
    // );
    // }

    // }
}
