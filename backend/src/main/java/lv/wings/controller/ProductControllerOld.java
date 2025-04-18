package lv.wings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.wings.model.entity.Product;
// import lv.wings.poi.PoiController;
import lv.wings.service.CRUDService;

@Controller
@RequestMapping("/prece")
public class ProductControllerOld {

	@Autowired
	private CRUDService<Product, Integer> productService;

	// @GetMapping("/getmapped") // localhost:8080/prece/show/all
	// public ResponseEntity<ArrayList<ProductDTO>> getDTOd() {
	// try {
	// List<Product> allProducts = productService.retrieveAll();
	// return ResponseEntity.ok(DTOMapper.mapMany(ProductDTO.class, allProducts.toArray(),
	// new String[] {"title", "productPicture.id"}));
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// e.printStackTrace();
	// return ResponseEntity.notFound().build();
	// }
	// }

	// @GetMapping("/getmapped/{id}") // localhost:8080/prece/show/all
	// public ResponseEntity<ProductDTO> getDTOdSingle(@PathVariable("id") int id) {
	// try {
	// Product selectedProduct = productService.retrieveById(id);
	// return ResponseEntity.ok(DTOMapper.map(ProductDTO.class, selectedProduct));
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// e.printStackTrace();
	// return ResponseEntity.notFound().build();
	// }
	// }

	// @GetMapping("/download/all") // localhost:8080/prece/download/all
	// public ResponseEntity<byte[]> downloadProducts() {
	// try {
	// List<Product> allProducts = productService.findAll();
	// // iegūt faila baitus no preces ar visiem pieejamiem laukiem
	// byte[] fileBytes = PoiController.buildMultiple("preces", allProducts, new String[] {});

	// HttpHeaders headers = new HttpHeaders();
	// headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	// headers.setContentDispositionFormData("attachment", "query_results.xlsx");

	// return ResponseEntity.ok().headers(headers).body(fileBytes);
	// } catch (Exception e) {
	// return ResponseEntity.notFound().build();
	// }
	// }

	// @GetMapping("/download/all/{id}") // localhost:8080/prece/download/all/{id}
	// public ResponseEntity<byte[]> downloadProductsById(@PathVariable("id") int id) {
	// try {
	// Product selectedProduct = productService.findById(id);
	// // iegūt faila baitus no preces ar definētiem laukiem
	// byte[] fileBytes = PoiController.buildSingle("prece-" + id, selectedProduct,
	// new String[] {"prece_id", "nosaukums", "cena", "daudzums"});

	// HttpHeaders headers = new HttpHeaders();
	// headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	// headers.setContentDispositionFormData("attachment", "query_results.xlsx");

	// return ResponseEntity.ok().headers(headers).body(fileBytes);
	// } catch (Exception e) {
	// return ResponseEntity.notFound().build();
	// }
	// }


	// @GetMapping(value = "/show/all")
	// public ResponseEntity<ApiListResponse<ProductDTO>> getProducts() {

	// try {
	// List<Product> allProducts = productService.retrieveAll();

	// return ResponseEntity.ok(new ApiListResponse<>(null,
	// DTOMapper.mapMany(ProductDTO.class, allProducts.toArray(), new String[] {"productPicture.id"})));
	// } catch (NoContentException e) {
	// return ResponseEntity.ok(new ApiListResponse<>(e.getMessage(), null));
	// } catch (Exception e) {
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }

	// }

	// @GetMapping(value = "/show/{id}")
	// public ResponseEntity<ApiResponse<ProductDTO>> getSingleProduct(@PathVariable("id") int id) {

	// try {
	// return ResponseEntity
	// .ok(new ApiResponse<>(null, DTOMapper.map(ProductDTO.class, productService.retrieveById(id))));
	// } catch (NoContentException e) {
	// return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
	// } catch (Exception e) {
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

	// @GetMapping(value = "/show/category/{categoryid}")
	// public ResponseEntity<ApiListResponse<ProductDTO>> getProductsByCategory(
	// @PathVariable("categoryid") int categoryId) {

	// try {
	// List<Product> allProducts = productFilteringService.selectAllByProductCategory(categoryId);

	// return ResponseEntity.ok(new ApiListResponse<>(null,
	// DTOMapper.mapMany(ProductDTO.class, allProducts.toArray(), new String[] {"productPicture.id"})));
	// } catch (NoContentException e) {
	// return ResponseEntity.ok(new ApiListResponse<>(e.getMessage(), null));
	// } catch (Exception e) {
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

	// @GetMapping("random")
	// public ResponseEntity<ApiResponse<ArrayList<ProductDTO>>> randomProducts() {
	// try {
	// List<Product> randomProducts = productFilteringService.randomProducts();

	// /// Lai main skata izvilktu tikai 1 attēlu katrai precei
	// for (Product product : randomProducts) {

	// Collection<ProductPicture> onlyFirstPictureList = new ArrayList<>();
	// if (!product.getProductPictures().isEmpty()) {
	// List<ProductPicture> pictureList = new ArrayList<>(product.getProductPictures());

	// int randomIndex = new Random().nextInt(pictureList.size());

	// ProductPicture randomPicture = pictureList.get(randomIndex);

	// onlyFirstPictureList.add(randomPicture);
	// }

	// product.setProductPictures(onlyFirstPictureList);
	// }

	// ArrayList<ProductDTO> productsDTO = DTOMapper.mapMany(ProductDTO.class, randomProducts.toArray(),
	// new String[] {
	// "productCategory.events", "productPictures.event", "productPictures.description",
	// "productCategory", "description"
	// });

	// return ResponseEntity.ok(new ApiResponse<>(null, productsDTO));
	// } catch (NoContentException e) {
	// return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ResponseEntity.internalServerError().body(
	// new ApiResponse<>(e.getMessage(), null));
	// }
	// }
}
