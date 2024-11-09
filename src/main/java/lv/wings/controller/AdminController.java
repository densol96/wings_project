package lv.wings.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.wings.model.Event;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping("/admin/api/")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
	private ICRUDService<Event> eventsRepo;


    @PostMapping(value = "events/add")
	public ResponseEntity<?> postCreateEvent(@Valid @RequestBody Event event, BindingResult result) {
		 
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
			.stream()
			.map(error -> error.getDefaultMessage())
			.collect(Collectors.toList());

			return ResponseEntity.badRequest().body(errors);
		}
		
		try {
			eventsRepo.create(event);
			return ResponseEntity.status(HttpStatus.CREATED).body("Jaunums - pasakums izveidots");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kļūda: " + e.getMessage());
		}
	}

}
