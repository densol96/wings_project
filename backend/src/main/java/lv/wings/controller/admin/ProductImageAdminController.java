package lv.wings.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.ImageDto;
import lv.wings.service.impl.ProductImageService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductImageAdminController {
    private final ProductImageService productImageService;

    @GetMapping("/{productId}/images")
    public ResponseEntity<List<ImageDto>> getImagesPerProduct(@PathVariable Integer productId) {
        log.info("Received GET request on /api/v1/admin/products/{}/images", productId);
        return ResponseEntity.ok(productImageService.getImagesAsDtoPerOwnerId(productId));
    }

    @PostMapping(path = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BasicMessageDto> uploadImagesPerProduct(@PathVariable Integer productId, @RequestParam("images") List<MultipartFile> images) {
        log.info("Received POST request on /api/v1/admin/products/{}/images", productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productImageService.addMoreImages(productId, images));
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<BasicMessageDto> deleteImageFromProduct(@PathVariable Integer imageId) {
        log.info("Received DELETE request on /api/v1/admin/products/images/{}", imageId);
        return ResponseEntity.ok(productImageService.deleteImage(imageId));
    }

}
