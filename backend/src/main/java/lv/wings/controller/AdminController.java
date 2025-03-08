package lv.wings.controller;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lv.wings.dto.DTOMapper;
import lv.wings.dto.object.EventCategoryDTO;
import lv.wings.dto.object.EventDTO;
import lv.wings.dto.object.EventPictureDTO;
import lv.wings.dto.object.ProductCategoryDTO;
import lv.wings.dto.object.ProductDTO;
import lv.wings.dto.object.ProductPictureDTO;
import lv.wings.exception.old.NoContentException;
import lv.wings.model.Event;
import lv.wings.model.EventCategory;
import lv.wings.model.EventPicture;
import lv.wings.model.Product;
import lv.wings.model.ProductCategory;
import lv.wings.model.ProductPicture;
import lv.wings.responses.ApiListResponse;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping("/admin/api/")
public class AdminController {

	@Autowired
	private ICRUDService<Event> eventsService;
	@Autowired
	private ICRUDService<EventCategory> eventsCategoryService;
	@Autowired
	private ICRUDService<EventPicture> eventsPictureService;

	@Autowired
	private ICRUDService<Product> productsService;
	@Autowired
	private ICRUDService<ProductCategory> productsCategoryService;
	@Autowired
	private ICRUDService<ProductPicture> productsPictureService;

	@Value("${upload.directory.events}")
	private String uploadEventsDir;

	@Value("${upload.directory.products}")
	private String uploadProductsDir;

	// @PostMapping(value = "events/create", consumes =
	// MediaType.MULTIPART_FORM_DATA_VALUE)
	// public ResponseEntity<ApiResponse<?>> postCreateEvent(@Valid @RequestPart
	// EventDTO eventDTO,
	// BindingResult result, @RequestPart(required = false) List<MultipartFile>
	// pictures) {

	// if (result.hasErrors()) {
	// List<String> errors = result.getAllErrors()
	// .stream()
	// .map(error -> error.getDefaultMessage())
	// .collect(Collectors.toList());

	// ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi
	// ievades lauki!", errors);
	// return ResponseEntity.badRequest().body(errorResponse);
	// }

	// try {
	// EventCategory eventCategory =
	// eventsCategoryService.retrieveById(eventDTO.getEventCategory().getId());
	// Event event = new Event(
	// eventDTO.getStartDate(),
	// eventDTO.getEndDate(),
	// eventDTO.getTitle(),
	// eventDTO.getLocation(),
	// eventDTO.getDescription(),
	// "key words", /// need to remove this probably
	// eventCategory);

	// if (pictures != null) {
	// Collection<EventPicture> eventPictures = new ArrayList<EventPicture>();
	// for (MultipartFile picture : pictures) {
	// String uniqFileName = UUID.randomUUID()
	// .toString()
	// .substring(0, 12) + "-" + picture.getOriginalFilename();
	// Path imagePath = Paths.get(uploadEventsDir, uniqFileName);

	// EventPicture eventPicture = new EventPicture(
	// uniqFileName,
	// picture.getOriginalFilename(),
	// "apraksts", /// we need this?
	// event);

	// eventPictures.add(eventPicture);
	// Files.write(imagePath, picture.getBytes());
	// }

	// event.setEventPictures(eventPictures);
	// }

	// eventsService.create(event);
	// return ResponseEntity.status(HttpStatus.CREATED).body(new
	// ApiResponse<>("Jaunums izveidots!", null));
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// .body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
	// }

	// }

	@PostMapping(value = "products/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>> postCreateProduct(@Valid @RequestPart ProductDTO productDTO,
			BindingResult result, @RequestPart(required = false) List<MultipartFile> pictures) {

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			ProductCategory productCategory = productsCategoryService
					.retrieveById(productDTO.getProductCategory().getId());

			Product product = new Product(
					productDTO.getTitle(),
					productDTO.getDescription(),
					productDTO.getPrice(),
					productDTO.getAmount(),
					productCategory);

			if (pictures != null) {
				Collection<ProductPicture> productPictures = new ArrayList<ProductPicture>();
				for (MultipartFile picture : pictures) {
					String uniqFileName = UUID.randomUUID()
							.toString()
							.substring(0, 12) + "-" + picture.getOriginalFilename();
					Path imagePath = Paths.get(uploadProductsDir, uniqFileName);

					ProductPicture productPicture = new ProductPicture(
							uniqFileName,
							"apraksts", /// we need this?
							product);

					productPictures.add(productPicture);
					Files.write(imagePath, picture.getBytes());
				}

				product.setProductPictures(productPictures);
			}

			productsService.create(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Prece izveidota!", null));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
		}

	}

	@PostMapping(value = "events-categories/create")
	public ResponseEntity<ApiResponse<?>> postCreateEventCategory(
			@Valid @RequestBody EventCategoryDTO eventCategory,
			BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			eventsCategoryService.create(
					new EventCategory(eventCategory.getTitle()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse<>("Jaunuma kategorija izveidota!", null));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
		}

	}

	@PostMapping(value = "products-categories/create")
	public ResponseEntity<ApiResponse<?>> postCreateProductCategory(
			@Valid @RequestBody ProductCategoryDTO productCategory,
			BindingResult result) {

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			productsCategoryService.create(
					new ProductCategory(productCategory.getTitle(), "apraksts"));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse<>("Produkta kategorija izveidota!", null));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
		}

	}

	// @PostMapping(value = "events-pictures/create-delete", consumes =
	// MediaType.MULTIPART_FORM_DATA_VALUE)
	// public ResponseEntity<ApiResponse<?>> postCreateAndDeleteEventPicture(
	// @Valid @RequestPart EventPictureDTO eventPictureDTO,
	// BindingResult result, @RequestParam(required = false) List<Integer>
	// deleteIds,
	// @RequestPart(required = false) List<MultipartFile> pictures) {

	// if (result.hasErrors()) {
	// List<String> errors = result.getAllErrors()
	// .stream()
	// .map(error -> error.getDefaultMessage())
	// .collect(Collectors.toList());

	// ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi
	// ievades lauki!", errors);
	// return ResponseEntity.badRequest().body(errorResponse);
	// }

	// try {
	// Event event = eventsService.retrieveById(eventPictureDTO.getId());
	// Collection<EventPicture> eventPictures = event.getEventPictures();

	// if (deleteIds != null) {
	// for (Integer pictureId : deleteIds) {
	// EventPicture eventPicture = eventsPictureService.retrieveById(pictureId);
	// eventPictures.remove(eventPicture);
	// Path deleteImagePath = Paths.get(uploadEventsDir,
	// eventPicture.getImageUrl());
	// Files.deleteIfExists(deleteImagePath);
	// }
	// }

	// if (pictures != null) {
	// for (MultipartFile picture : pictures) {
	// String uniqFileName = UUID.randomUUID().toString().substring(0, 12) + "-"
	// + picture.getOriginalFilename();
	// Path imagePath = Paths.get(uploadEventsDir, uniqFileName);

	// EventPicture newEventPicture = new EventPicture(
	// uniqFileName,
	// picture.getOriginalFilename(),
	// "Bildes apraksts!",
	// event);

	// eventPictures.add(newEventPicture);
	// Files.write(imagePath, picture.getBytes());
	// }
	// }

	// event.setEventPictures(eventPictures);
	// eventsService.update(event.getId(), event);

	// return ResponseEntity.status(HttpStatus.CREATED).body(new
	// ApiResponse<>("Attēli atjaunoti!", null));
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// .body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
	// }

	// }

	@PostMapping(value = "products-pictures/create-delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>> postCreateAndDeleteProductPicture(
			@Valid @RequestPart ProductPictureDTO productPictureDTO,
			BindingResult result, @RequestParam(required = false) List<Integer> deleteIds,
			@RequestPart(required = false) List<MultipartFile> pictures) {

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			Product product = productsService.retrieveById(productPictureDTO.getId());
			Collection<ProductPicture> productsPictures = product.getProductPictures();

			if (deleteIds != null) {
				for (Integer pictureId : deleteIds) {
					ProductPicture productPicture = productsPictureService.retrieveById(pictureId);
					productsPictures.remove(productPicture);
					Path deleteImagePath = Paths.get(uploadProductsDir, productPicture.getReferenceToPicture());
					Files.deleteIfExists(deleteImagePath);
				}
			}

			if (pictures != null) {
				for (MultipartFile picture : pictures) {
					String uniqFileName = UUID.randomUUID().toString().substring(0, 12) + "-"
							+ picture.getOriginalFilename();
					Path imagePath = Paths.get(uploadProductsDir, uniqFileName);

					ProductPicture newProductPicture = new ProductPicture(uniqFileName, "apraksts", product);

					productsPictures.add(newProductPicture);
					Files.write(imagePath, picture.getBytes());
				}
			}

			product.setProductPictures(productsPictures);

			// System.out.println(product.getProductPictures().size());
			// for (ProductPicture p : product.getProductPictures()){
			// System.out.println(p.toString());
			// }

			productsService.update(product.getProductId(), product);

			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Attēli atjaunoti!", null));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
		}

	}

	@GetMapping(value = "events-categories")
	public ResponseEntity<ApiListResponse<EventCategoryDTO>> getAllEventCategories() {
		try {
			List<EventCategory> allEventCategories = eventsCategoryService.retrieveAll();
			List<EventCategoryDTO> eventCategoriesDTO = DTOMapper.mapMany(
					EventCategoryDTO.class, allEventCategories.toArray(), new String[] { "events" });

			return ResponseEntity.ok(new ApiListResponse<>(null, eventCategoriesDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiListResponse<>(e.getMessage(), List.of()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "products-categories")
	public ResponseEntity<ApiListResponse<?>> getAllProductCategories() {
		try {
			List<ProductCategory> allProductCategories = productsCategoryService.retrieveAll();
			List<ProductCategoryDTO> productCategoriesDTO = DTOMapper.mapMany(
					ProductCategoryDTO.class, allProductCategories.toArray());

			return ResponseEntity.ok(new ApiListResponse<>(null, productCategoriesDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping(value = "events")
	public ResponseEntity<ApiResponse<?>> getAllEvents() {
		try {
			List<Event> allEvents = eventsService.retrieveAll();
			List<EventDTO> eventsDTO = DTOMapper.mapMany(EventDTO.class, allEvents.toArray(),
					new String[] { "eventCategory", "location", "startDate", "endDate", "description", "lastModified",
							"createdBy", "lastModifiedBy" });

			return ResponseEntity.ok(new ApiResponse<>(null, eventsDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}

	}

	@GetMapping(value = "products")
	public ResponseEntity<ApiResponse<?>> getAllProducts() {
		try {
			List<Product> allProducts = productsService.retrieveAll();
			List<ProductDTO> productsDTO = DTOMapper.mapMany(ProductDTO.class, allProducts.toArray(),
					new String[] { "productCategory", "purchaseElement", "description", "price", "amount",
							"lastModified", "createdBy", "lastModifiedBy" });

			return ResponseEntity.ok(new ApiResponse<>(null, productsDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}

	}

	@GetMapping(value = "events/{id}")
	public ResponseEntity<ApiResponse<?>> getEvent(@PathVariable int id) {
		try {
			Event event = eventsService.retrieveById(id);
			EventDTO eventDTO = DTOMapper.map(EventDTO.class, event,
					new String[] { "eventPictures", "eventCategory.events" });

			return ResponseEntity.ok(new ApiResponse<>(null, eventDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}

	}

	@GetMapping(value = "products/{id}")
	public ResponseEntity<ApiResponse<?>> getProduct(@PathVariable int id) {
		try {
			Product product = productsService.retrieveById(id);
			ProductDTO productDTO = DTOMapper.map(ProductDTO.class, product,
					new String[] { "productPictures", "productCategory.products", });

			return ResponseEntity.ok(new ApiResponse<>(null, productDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}

	}

	// @PutMapping(value = "events/{id}")
	// public ResponseEntity<ApiResponse<?>> updateEvent(@PathVariable int id,
	// @RequestBody EventDTO eventDTO,
	// BindingResult result) {

	// if (result.hasErrors()) {
	// List<String> errors = result.getAllErrors()
	// .stream()
	// .map(error -> error.getDefaultMessage())
	// .collect(Collectors.toList());

	// ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi
	// ievades lauki!", errors);
	// return ResponseEntity.badRequest().body(errorResponse);
	// }
	// try {
	// Event event = eventsService.retrieveById(id);
	// EventCategory eventCategory =
	// eventsCategoryService.retrieveById(eventDTO.getEventCategory().getId());

	// event.setTitle(eventDTO.getTitle());
	// event.setLocation(eventDTO.getLocation());
	// event.setStartDate(eventDTO.getStartDate());
	// event.setEndDate(eventDTO.getEndDate());
	// event.setDescription(eventDTO.getDescription());
	// event.setCategory(eventCategory);

	// eventsService.update(event.getId(), event);

	// return ResponseEntity.ok(new ApiResponse<>(null, eventDTO));
	// } catch (NoContentException e) {
	// return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ResponseEntity.internalServerError().body(
	// new ApiResponse<>(e.getMessage(), null));
	// }
	// }

	@PutMapping(value = "products/{id}")
	public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO,
			BindingResult result) {

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}
		try {
			Product product = productsService.retrieveById(id);
			ProductCategory productCategory = productsCategoryService
					.retrieveById(productDTO.getProductCategory().getId());

			product.setTitle(productDTO.getTitle());
			product.setPrice(productDTO.getPrice());
			product.setAmount(productDTO.getAmount());
			product.setDescription(productDTO.getDescription());
			product.setProductCategory(productCategory);
			productsService.update(product.getProductId(), product);

			return ResponseEntity.ok(new ApiResponse<>(null, productDTO));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}
	}

	@DeleteMapping(value = "events-categories/{id}")
	public ResponseEntity<ApiResponse<EventCategory>> deleteEventCategory(@PathVariable int id) {

		try {
			EventCategory eventCategory = eventsCategoryService.retrieveById(id);

			eventsCategoryService.deleteById(id);

			return ResponseEntity.ok(new ApiResponse<EventCategory>(null, eventCategory));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<EventCategory>(e.getMessage(),
					null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping(value = "products-categories/{id}")
	public ResponseEntity<ApiResponse<ProductCategory>> deleteProductCategory(@PathVariable int id) {

		try {
			ProductCategory productCategory = productsCategoryService.retrieveById(id);
			productsCategoryService.deleteById(id);

			return ResponseEntity.ok(new ApiResponse<ProductCategory>(null, productCategory));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<ProductCategory>(e.getMessage(),
					null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping(value = "events/{id}")
	public ResponseEntity<ApiResponse<Event>> deleteEvent(@PathVariable int id) {

		try {
			Event event = eventsService.retrieveById(id);
			eventsService.deleteById(id);
			return ResponseEntity.ok(new ApiResponse<Event>(null, event));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<Event>(e.getMessage(),
					null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping(value = "products/{id}")
	public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable int id) {
		try {
			Product product = productsService.retrieveById(id);
			productsService.deleteById(id);
			return ResponseEntity.ok(new ApiResponse<Product>(null, product));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<Product>(e.getMessage(),
					null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PutMapping(value = "events-categories/{id}")
	public ResponseEntity<ApiResponse<?>> updateEventCategory(@PathVariable int id,
			@Valid @RequestBody EventCategoryDTO eventCategory,
			BindingResult result) {

		System.out.println(eventCategory.getTitle());

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			EventCategory foundEventCategory = eventsCategoryService.retrieveById(id);
			foundEventCategory.setTitle(eventCategory.getTitle());
			eventsCategoryService.update(id, foundEventCategory);

			return ResponseEntity.ok(new ApiResponse<EventCategory>(null, foundEventCategory));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<EventCategory>(e.getMessage(),
					null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(value = "products-categories/{id}")
	public ResponseEntity<ApiResponse<?>> updateProductCategory(@PathVariable int id,
			@Valid @RequestBody ProductCategoryDTO productCategory,
			BindingResult result) {

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			ProductCategory foundProductCategory = productsCategoryService.retrieveById(id);
			foundProductCategory.setTitle(productCategory.getTitle());
			productsCategoryService.update(id, foundProductCategory);

			return ResponseEntity.ok(new ApiResponse<ProductCategory>(null, foundProductCategory));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<ProductCategory>(e.getMessage(),
					null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
