package lv.wings.controller;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.service.OrderService;
import lv.wings.service.PaymentService;
import lv.wings.service.S3Service;
import lv.wings.service.TranslationService;

@Getter
class TestClass {
    private String PRIVATE_FIELD = "PRIVATE_FIELD";
    public String PUBLIC_FIELD = "PUBLIC_FIELD";
    String FIELD = "FIELD";
}


@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final TranslationService translationService;
    private final S3Service s3Service;

    @GetMapping("/test")
    public String test(@RequestParam String text) {
        log.info("THE CONTROLLER METHOD ACTUALLY RUNS!");
        return translationService.translateToEnglish(text);
    }


    @PostMapping("/test")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String url = s3Service.uploadFile(file, "products");
        return ResponseEntity.ok(url);
    }
}
