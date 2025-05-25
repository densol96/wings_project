package lv.wings.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.request.admin.products.NewCategoryDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.products.AdminProductCategoryDto;
import lv.wings.dto.response.admin.products.EditCategoryDto;
import lv.wings.service.ProductCategoryService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/products/categories")
@RequiredArgsConstructor
public class ProductAdminCategoryController {
    private final ProductCategoryService productCategoryService;

    @GetMapping
    @AllowedSortFields({"createdAt", "lastModifiedAt"})
    public ResponseEntity<List<AdminProductCategoryDto>> getCategories(Sort sort) {
        log.info("Received GET request on /api/v1/admin/products/categories");
        return ResponseEntity.ok(productCategoryService.getAllAdminCategories(sort));
    }

    @GetMapping("{id}")
    public ResponseEntity<EditCategoryDto> getCategory(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/admin/products/categories/{}", id);
        return ResponseEntity.ok(productCategoryService.getExistingCategoryForAdmin(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageDto> createCategory(@RequestBody NewCategoryDto dto) {
        log.info("Received POST request on /api/v1/admin/products/categories");
        return ResponseEntity.ok(productCategoryService.createCategory(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageDto> updateCategory(@PathVariable Integer id, @RequestBody NewCategoryDto dto) {
        log.info("Received PUT request on /api/v1/admin/products/categories/{}", id);
        return ResponseEntity.ok(productCategoryService.updateCategory(dto, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageDto> deleteCategory(@PathVariable Integer id) {
        log.info("Received DELETE request on /api/v1/admin/products/categories/{id}", id);
        return ResponseEntity.ok(productCategoryService.deleteCategory(id));
    }
}
