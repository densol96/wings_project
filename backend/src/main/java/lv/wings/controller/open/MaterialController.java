package lv.wings.controller.open;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.dto.response.product.MaterialDto;
import lv.wings.service.MaterialService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<MaterialDto>> getProductById() {
        log.info("Received GET request on /api/v1/materials.");
        return ResponseEntity.ok().body(materialService.getAllMaterials());
    }
}
