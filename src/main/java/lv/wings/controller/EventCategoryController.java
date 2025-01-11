package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.wings.exceptions.NoContentException;
import lv.wings.model.EventCategory;
import lv.wings.responses.ApiArrayListResponse;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/api/event-categories")
public class EventCategoryController {
	
	@Autowired
	private ICRUDService<EventCategory> eventCategoryRepo;

	@GetMapping(value = "")
	public ResponseEntity<ApiArrayListResponse<EventCategory>> getAllCategories() {

		try {
			ArrayList<EventCategory> allEventCategories = eventCategoryRepo.retrieveAll();

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allEventCategories));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	/*
	@GetMapping("/show/all")
	public String getAllEventCategories(Model model) {
		try {
			ArrayList<EventCategory> allEventCategories = eventCategoryRepo.retrieveAll();
			model.addAttribute("pasakumaKategorija",allEventCategories);
			model.addAttribute("msg", "Visas pasākuma kategorijas");
			return "pasakumaKategorija/pasakumaKategorija-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	*/
	
	

	@GetMapping("/show/all/{id}")
	public String getEventCategoryById(@PathVariable("id") int id, Model model) {
		try {
	    EventCategory selectedEventCategory = eventCategoryRepo.retrieveById(id);
			model.addAttribute("pasakumaKategorija",selectedEventCategory);
			model.addAttribute("msg", "Pasākuma kategorija pēc id");
			return "pasakumaKategorija/pasakumaKategorija-all-page";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/remove/{id}")
	public String getDeleteEventCategoryById(@PathVariable("id") int id, Model model) {
		try {
			eventCategoryRepo.deleteById(id);
			return "redirect:/pasakuma-kategorija/show/all";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/add")
	public String getInsertEventCategory(Model model) {
		model.addAttribute("pasakumaKategorija", new EventCategory());
		return "pasakumaKategorija/pasakumaKategorija-add-page";
	} 
	
	@PostMapping("/add")
	public String postInsertEventCategory(@Valid EventCategory eventCategory, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			return "pasakumaKategorija/pasakumaKategorija-add-page";
		} else {
			eventCategoryRepo.create(eventCategory);
			return "redirect:/pasakuma-kategorija/show/all";
		}
	}
	
	@GetMapping("/update/{id}") 
	public String getUpdateEventCategoryById(@PathVariable("id") int id, Model model) {
		try {
			EventCategory eventCategoryForUpdating = eventCategoryRepo.retrieveById(id);
			model.addAttribute("pasakumaKategorija",eventCategoryForUpdating);
			model.addAttribute("id", id);
			return "pasakumaKategorija/pasakumaKategorija-update-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/update/{id}") 
	public String postUpdateEventCategoryById(@PathVariable("id") int id,@Valid EventCategory eventCategory, BindingResult result, Model model) {
		try {
			if(result.hasErrors()) {
				return "atlaide/atlaide-update-page";
			}
			eventCategoryRepo.update(id, eventCategory);
			return "redirect:/pasakuma-kategorija/show/all/"+ id;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
		
	}

}
