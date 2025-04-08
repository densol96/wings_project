package lv.wings.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.RandomProductDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.service.ProductService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @AllowedSortFields({"createdAt", "price"})
    public ResponseEntity<Page<ShortProductDto>> getProductsPerCatalogPage(
            @RequestParam(defaultValue = "0") Integer categoryId,
            @PageableDefault(page = 1, size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Received GET request on /api/v1/products with categoryId of {}.", categoryId);
        return ResponseEntity.ok().body(productService.getAllByCategory(categoryId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/products/{}.", id);
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @GetMapping("/random")
    public ResponseEntity<List<RandomProductDto>> getRandomProducts(
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "1") Integer amount) {
        log.info("Received GET request on /api/v1/products/random with category id of {} and amount of {}", categoryId, amount);
        return ResponseEntity.ok().body(productService.getRandomProducts(categoryId, amount));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchedProductDto>> searchProducts(
            @RequestParam String q) {
        log.info("Received GET request on /api/v1/products/search with q parameter of {}", q);
        return ResponseEntity.ok().body(productService.getSearchedProducts(q));
    }

}
