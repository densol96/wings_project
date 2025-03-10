package lv.wings.controller;

import java.util.ArrayList;
import java.util.List;

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
import lv.wings.exception.old.NoContentException;
import lv.wings.model.entity.EventPicture;
import lv.wings.responses.ApiListResponse;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/api/event-pictures")
public class EventPictureController {
	@Autowired
	private ICRUDService<EventPicture> eventPictureRepo;

	@GetMapping(value = "")
	public ResponseEntity<ApiListResponse<EventPicture>> getAllPictures() {
		try {
			List<EventPicture> allEventPictures = eventPictureRepo.retrieveAll();
			return ResponseEntity.ok(new ApiListResponse<>(null, allEventPictures));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/show/all/{id}")
	public String getEventPictureById(@PathVariable("id") int id, Model model) {
		try {
			EventPicture selectedEventPicture = eventPictureRepo.retrieveById(id);
			model.addAttribute("pasakumaBilde", selectedEventPicture);
			model.addAttribute("msg", "Pasākuma bilde pēc id");
			return "pasakumaBilde/pasakumaBilde-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	@GetMapping("/remove/{id}")
	public String getDeleteEventPictureById(@PathVariable("id") int id, Model model) {
		try {
			eventPictureRepo.deleteById(id);
			return "redirect:/pasakuma-bilde/show/all";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	@GetMapping("/add")
	public String getInsertEventPicture(Model model) {
		model.addAttribute("pasakumaBilde", new EventPicture());
		return "pasakumaBilde/pasakumaBilde-add-page";
	}

	@PostMapping("/add")
	public String postInsertEventPicture(@Valid EventPicture eventPicture, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return "pasakumaBilde/pasakumaBilde-add-page";
		} else {
			eventPictureRepo.create(eventPicture);
			return "redirect:/pasakuma-bilde/show/all";
		}
	}

	@GetMapping("/update/{id}")
	public String getUpdateEventPictureById(@PathVariable("id") int id, Model model) {
		try {
			EventPicture eventPictureForUpdating = eventPictureRepo.retrieveById(id);
			model.addAttribute("pasakumaBilde", eventPictureForUpdating);
			model.addAttribute("id", id);
			return "pasakumaBilde/pasakumaBilde-update-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	@PostMapping("/update/{id}")
	public String postUpdateEventPictureById(@PathVariable("id") int id, @Valid EventPicture eventPicture,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return "pasakumaBilde/pasakumaBilde-update-page";
			}
			eventPictureRepo.update(id, eventPicture);
			return "redirect:/pasakuma-bilde/show/all/" + id;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}

}
