package lv.wings.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import lv.wings.dto.post.EventDTO;
import lv.wings.dto.post.CreateEventCategoryDTO;
import lv.wings.dto.post.CreateEventPictureDTO;
import lv.wings.exceptions.NoContentException;
import lv.wings.model.Event;
import lv.wings.model.EventCategory;
import lv.wings.model.EventPicture;
import lv.wings.responses.ApiArrayListResponse;
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

	@Value("${upload.directory.events}")
	private String uploadEventsDir;

	
		@PostMapping(value = "events/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<ApiResponse<?>> postCreateEvent(@Valid @RequestPart EventDTO eventDTO,
		BindingResult result, @RequestPart(required = false) List<MultipartFile> pictures) {
			
			if (result.hasErrors()) {
				List<String> errors = result.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
	
				ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
				return ResponseEntity.badRequest().body(errorResponse);
			}
	
			Event event = new Event();
			event.setTitle(eventDTO.getTitle());
			event.setStartDate(eventDTO.getStartDate());
			event.setDescription(eventDTO.getDescription());
			event.setEndDate(eventDTO.getEndDate());
			event.setLocation(eventDTO.getLocation());
			event.setKeyWords(eventDTO.getKeyWords());
			
			

			try {
				EventCategory eventCategory = eventsCategoryService.retrieveById(eventDTO.getEventCategoryId());
				event.setEventCategory(eventCategory);
				
				
				if (pictures != null){
				Collection<EventPicture> eventPictures = new ArrayList<EventPicture>();
				for (MultipartFile picture : pictures){
					String uniqFileName = UUID.randomUUID()
					.toString()
					.substring(0, 12) + "-" + picture.getOriginalFilename();
					Path imagePath = Paths.get(uploadEventsDir, uniqFileName);

					
					EventPicture eventPicture = new EventPicture(
						uniqFileName,
						picture.getOriginalFilename(),
						"apraksts", /// we need this?
						event
					);

					eventPictures.add(eventPicture);
					Files.write(imagePath, picture.getBytes());
				}

					event.setEventPictures(eventPictures);
				}
				
				eventsService.create(event);
				return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Jaunums izveidots!", null));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Kļūda: "+e.getMessage(), null));
			}
				

			
		}



	@PostMapping(value = "events-categories/create")
	public ResponseEntity<ApiResponse<?>> postCreateEventCategory(@Valid @RequestBody CreateEventCategoryDTO eventCategory,
	BindingResult result){

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
			.stream()
			.map(error -> error.getDefaultMessage())
			.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			EventCategory newEventCategory = new EventCategory(eventCategory.getTitle());
			eventsCategoryService.create(newEventCategory);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Jaunuma kategorija izveidota!", null));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ApiResponse<>("Kļūda: "+e.getMessage(), null));
		}
			
	}
  
	@PostMapping(value = "events-pictures/create-delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>> postCreateAndDeleteEventPicture(@Valid @RequestPart CreateEventPictureDTO eventPictureDTO,
	BindingResult result, @RequestParam(required = false) ArrayList<Integer> deleteIds,  @RequestPart(required = false) List<MultipartFile> pictures) {

			
			
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
			.stream()
			.map(error -> error.getDefaultMessage())
			.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		
		try { 
			Event event = eventsService.retrieveById(eventPictureDTO.getEventId());
			Collection<EventPicture> eventPictures = event.getEventPictures();
			

			if (deleteIds != null){
				//Collection<EventPicture> eventPictures = event.getEventPictures();

				for (Integer pictureId : deleteIds){
					EventPicture eventPicture = eventsPictureService.retrieveById(pictureId);
					eventPictures.remove(eventPicture);
					Path deleteImagePath = Paths.get(uploadEventsDir, eventPicture.getReferenceToPicture());
					Files.deleteIfExists(deleteImagePath);
				}

				//event.setEventPictures(eventPictures);
				//eventsService.update(event.getEventId(), event);
			}

			if (pictures != null){
				//List<EventPicture> eventPictures = new ArrayList<>();
				for (MultipartFile picture : pictures){
					String uniqFileName = UUID.randomUUID().toString().substring(0, 12) + "-" + picture.getOriginalFilename();
					Path imagePath = Paths.get(uploadEventsDir, uniqFileName);
	
					
					//Event foundEvent = eventsRepo.retrieveById(id);
					EventPicture newEventPicture = new EventPicture(
						uniqFileName, 
						picture.getOriginalFilename(), 
						"Bildes apraksts!", 
						event
					);
					//eventPictures.add(eventPic);
					eventPictures.add(newEventPicture);
					Files.write(imagePath, picture.getBytes());
				}
				
				//for (EventPicture picture : eventPictures){
				//	eventsPictureService.create(picture);
				//}
			}

			event.setEventPictures(eventPictures);
			eventsService.update(event.getEventId(), event);
				
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Attēli atjaunoti!", null));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Kļūda: "+e.getMessage(), null));
		}
			
	}


	
	@GetMapping(value = "events-categories")
	public ResponseEntity<ApiArrayListResponse<EventCategory>> getAllEventCategories() {

		try {
			ArrayList<EventCategory> allEventCategories = eventsCategoryService.retrieveAll();

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allEventCategories));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

		
	@GetMapping(value = "events-pictures")
	public ResponseEntity<ApiArrayListResponse<EventPicture>> getAllEventPictures() {

		try {
			ArrayList<EventPicture> allEventCategories = eventsPictureService.retrieveAll();

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allEventCategories));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	
	
	/* 
	This will be updated 

	@DeleteMapping(value = "events/{id}")
	public ResponseEntity<ApiResponse<Event>> deleteEvent(@PathVariable int id) {
		
		System.out.println(id);

		try {
			Event event = eventsService.retrieveById(id);


			eventsService.deleteById(id);

			return ResponseEntity.ok(new ApiResponse<Event>(null,event));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<Event>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping(value = "events-categories/{id}")
	public ResponseEntity<ApiResponse<EventCategory>> deleteEventCategory(@PathVariable int id) {
		

		try {
			EventCategory eventCategory = eventsCategoryService.retrieveById(id);


			eventsCategoryService.deleteById(id);

			return ResponseEntity.ok(new ApiResponse<EventCategory>(null,eventCategory));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<EventCategory>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping(value = "events-pictures/{id}")
	public ResponseEntity<ApiResponse<EventPicture>> deleteEventPicture(@PathVariable int id) {
		try {
			EventPicture eventPicture = eventsPictureService.retrieveById(id);


			eventsPictureService.deleteById(id);

			return ResponseEntity.ok(new ApiResponse<EventPicture>(null,eventPicture));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<EventPicture>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	

	@PutMapping(value = "events-categories/{id}")
	public ResponseEntity<ApiResponse<?>> updateEventCategory(@PathVariable int id, @Valid @RequestBody EventCategory eventCategory,
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
			EventCategory foundEventCategory = eventsCategoryService.retrieveById(id);
			

			foundEventCategory.setTitle(eventCategory.getTitle());

			eventsCategoryService.update(id, foundEventCategory);

	
			return ResponseEntity.ok(new ApiResponse<EventCategory>(null,eventCategory));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<EventCategory>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	*/
}
