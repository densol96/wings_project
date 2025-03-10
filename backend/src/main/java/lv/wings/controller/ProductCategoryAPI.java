package lv.wings.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.wings.exception.old.NoContentException;
import lv.wings.model.entity.ProductCategory;
import lv.wings.responses.ApiListResponse;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping(value = "/api/productcategory")
public class ProductCategoryAPI {

	@Autowired
	private ICRUDService<ProductCategory> productCategoryService;

	@GetMapping(value = "/show/all")
	public ResponseEntity<ApiListResponse<ProductCategory>> getProductCategories() {

		try {
			List<ProductCategory> allProductCategories = productCategoryService.retrieveAll();

			return ResponseEntity.ok(new ApiListResponse<>(null, allProductCategories));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
