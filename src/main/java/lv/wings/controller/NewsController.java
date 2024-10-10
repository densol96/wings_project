package lv.wings.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.wings.exceptions.NoContentException;
import lv.wings.model.Pasakums;
import lv.wings.responses.ApiArrayListResponse;
import lv.wings.responses.ApiResponse;
import lv.wings.service.IPasakumiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/news")
public class NewsController {

	@Autowired
	private IPasakumiService pasakumsRepo;

	// @Autowired
	// private IPasakumaKomentarsService pasakumaKomentarsRepo;

	@GetMapping(value = "")
	public ResponseEntity<ApiArrayListResponse<Pasakums>> getAllNews() {

		try {
			ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumi();

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allPasakumi));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping(value = "/show/{id}")
	public ResponseEntity<ApiResponse<Pasakums>> getSingleNews(@PathVariable("id") int id) {

		try {
			return ResponseEntity.ok(new ApiResponse<>(null, pasakumsRepo.selectPaskumsById(id)));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> postAddNews(@Valid @RequestBody Pasakums pasakums /*,BindingResult result*/) {
		/* 
		if (result.hasErrors()) {
			 This show error because of wrong date formats need to fix
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
			*/
		try {
			pasakumsRepo.create(pasakums);
			return ResponseEntity.status(HttpStatus.CREATED).body("Jaunums - pasakums izveidots");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kļūda: " + e.getMessage());
		}
	}

}
