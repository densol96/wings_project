package lv.wings.controller;

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


    @GetMapping("images/events/{picturePath}")
    public ResponseEntity<?> getEventImage(@PathVariable String picturePath){

        //System.out.println(uploadEventsDir + "/" + picturePath);

        try {
            File imageFile = new File(uploadEventsDir + "/" + picturePath);

        
             if (!imageFile.exists()){
                ApiResponse<String> notFoundResponse = new ApiResponse<>("No picture found!", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            } 
    
            FileSystemResource fileSystemResource = new FileSystemResource(imageFile);

            String contentType = URLConnection.guessContentTypeFromName(imageFile.getName());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            return ResponseEntity.ok()
            .contentType(MediaType.valueOf(contentType))
            .body(fileSystemResource);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                new ApiResponse<>(e.getMessage(), null)
            );
        }
       
    }


    //// products

}
