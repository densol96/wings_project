package lv.wings.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.service.ProductCategoryService;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService service;

    @GetMapping
    public ResponseEntity<List<ProductCategoryWithAmountDto>> getAllCategories() {
        log.info("Received GET request on /api/v1/product-categories");
        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> getSingleCategory(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/product-categories/{}", id);
        return ResponseEntity.ok(service.getCategory(id));
    }

}
