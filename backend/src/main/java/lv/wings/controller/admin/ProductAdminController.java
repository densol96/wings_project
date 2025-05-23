package lv.wings.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.request.admin.products.NewProductDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.products.ExistingProductDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<ExistingProductDto> getProduct(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/admin/products/{}", id);
        return ResponseEntity.ok(productService.getExistingProductForAdmin(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageDto> deleteProduct(@PathVariable Integer id) {
        log.info("Received DELETE request on /api/v1/admin/products/{}", id);
        productService.deleteById(id);
        return ResponseEntity.ok(new BasicMessageDto("Produkts veiksmīgi dzēsts"));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BasicMessageDto> createProduct(@ModelAttribute NewProductDto dto) {
        log.info("Received POST request on /api/v1/admin/products/");
        return ResponseEntity.ok(productService.createProduct(dto));
    }
}
