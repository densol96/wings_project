package lv.wings.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.wings.dto.DTOMapper;
import lv.wings.dto.object.ProductDTO;
import lv.wings.exceptions.NoContentException;
import lv.wings.model.Product;
import lv.wings.model.ProductPicture;
import lv.wings.responses.ApiArrayListResponse;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;
import lv.wings.service.IProductsFilterService;


@RestController
@RequestMapping(value = "/api/products")
public class ProductAPI {

    @Autowired
    private ICRUDService<Product> productService;

    @Autowired
    private IProductsFilterService productFilteringService;

    @GetMapping(value = "/show/all")
	public ResponseEntity<ApiArrayListResponse<Product>> getProducts() {

		try {
			ArrayList<Product> allProducts = productService.retrieveAll();

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allProducts));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}


    @GetMapping(value = "/show/{id}")
	public ResponseEntity<ApiResponse<Product>> getSingleProduct(@PathVariable("id") int id) {

		try {
			return ResponseEntity.ok(new ApiResponse<>(null, productService.retrieveById(id)));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

    @GetMapping(value = "/show/category/{categoryid}")
	public ResponseEntity<ApiArrayListResponse<Product>> getProductsByCategory(@PathVariable("categoryid") int categoryId) {

		try {
			ArrayList<Product> allProducts = productFilteringService.selectAllByProductCategory(categoryId);

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allProducts));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}


	@GetMapping("random")
	public ResponseEntity<ApiResponse<ArrayList<ProductDTO>>> randomProducts(){
		try {
			ArrayList<Product> randomProducts = productFilteringService.randomProducts();

			/// Lai main skata izvilktu tikai 1 attēlu katrai precei
			for (Product product : randomProducts){

				Collection<ProductPicture> onlyFirstPictureList = new ArrayList<>();
				if (!product.getProductPictures().isEmpty()){
					List<ProductPicture> pictureList = new ArrayList<>(product.getProductPictures());

						int randomIndex = new Random().nextInt(pictureList.size());

					
						ProductPicture randomPicture = pictureList.get(randomIndex);

						onlyFirstPictureList.add(randomPicture);
				}

				product.setProductPictures(onlyFirstPictureList);
			}

			ArrayList<ProductDTO> productsDTO = DTOMapper.mapMany(ProductDTO.class, randomProducts.toArray(), new String[]{
				"productCategory.events", "productPictures.event", "productPictures.description", "productCategory", "description"
			});

			
			return ResponseEntity.ok(new ApiResponse<>(null, productsDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}
	
	}
}
