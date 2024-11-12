package lv.wings.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lv.wings.model.Event;
import lv.wings.model.EventPicture;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping("/admin/api/")
public class AdminController {

    @Autowired
	private ICRUDService<Event> eventsRepo;

	@Value("${upload.directory.events}")
	private String uploadEventsDir;


    @PostMapping(value = "events/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ApiResponse<?>> postCreateEvent(@Valid @RequestPart Event event,
	@RequestPart(value="images", required = false) List<MultipartFile> images,
	BindingResult result) {

		//System.out.println(event);
		//System.out.println(images);
		 
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
			.stream()
			.map(error -> error.getDefaultMessage())
			.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		
		
		try {
			if (images != null){
			
			List<EventPicture> eventPictures = new ArrayList<>();


			for (MultipartFile image : images){
				
				String uniqFileName = UUID.randomUUID().toString().substring(0, 12) + "-" + image.getOriginalFilename();
				Path imagePath = Paths.get(uploadEventsDir, uniqFileName);

				Files.write(imagePath, image.getBytes());

				EventPicture eventPicture = new EventPicture(uniqFileName, image.getOriginalFilename(), "Bildes apraksts!", event);
				eventPictures.add(eventPicture);

			}
			
			event.setEventPictures(eventPictures);
			}

			eventsRepo.create(event);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Jaunums izveidots!", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Kļūda: "+e.getMessage(), null));
		}
	}

}
