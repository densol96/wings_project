package lv.wings.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.response.admin.products.ProductAdminDto;
import lv.wings.service.ProductService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @GetMapping
    @AllowedSortFields({"createdAt", "lastModifiedAt", "sold", "amount"})
    public ResponseEntity<Page<ProductAdminDto>> getProducts(
            @PageableDefault(page = 1, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") Integer categoryId) {
        log.info("Received GET request on /api/v1/admin/products");
        return ResponseEntity.ok(productService.getAllByCategoryForAdmin(q, categoryId, pageable));
    }
}
