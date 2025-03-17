package lv.wings.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public String getProductsPerCatalogPage() {
        return new String();
    }

}
