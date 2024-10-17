package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.wings.model.PasakumaKomentars;
import lv.wings.model.Pasakums;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/pasakuma-komentars")
public class PasakumaKomentarsController {
	@Autowired
	private ICRUDService<PasakumaKomentars> pasakumaKomentarsRepo;

	@Autowired
	private ICRUDService<Pasakums> pasakumsRepo;

	@GetMapping("/show/all")
	public String getAllPasakumaKomentars(Model model) {
		try {
			ArrayList<PasakumaKomentars> allPasakumaKomentars = pasakumaKomentarsRepo.retrieveAll();
			model.addAttribute("pasakumaKomentars", allPasakumaKomentars);
			model.addAttribute("msg", "Visi pasakuma komentāri");
			return "pasakumaKomentars/pasakumaKomentars-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	@GetMapping("/show/all/{id}")
	public String getPasakumaKomentarsById(@PathVariable("id") int id, Model model) {
		try {
			PasakumaKomentars selectedPasakumaKomentars = pasakumaKomentarsRepo.retrieveById(id);
			model.addAttribute("pasakumaKomentars", selectedPasakumaKomentars);
			model.addAttribute("msg", "Pasākuma komentārs pēc id");
			return "pasakumaKomentars/pasakumaKomentars-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	@GetMapping("/remove/{id}")
	public String getDeletePasakumaKomentarsById(@PathVariable("id") int id, Model model) {
		try {
			pasakumaKomentarsRepo.deleteById(id);
			return "redirect:/pasakuma-komentars/show/all";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}

	/*
	@GetMapping("/add")
	public String getPasakumaKomentarsInsert(Model model) {

		try {
			//Pasakums pasakums = pasakumsRepo.selectPaskumsById(pasakumsId);
			model.addAttribute("pasakumaKomentars", new PasakumaKomentars());
			//model.addAttribute("pasakumsId", pasakumsId);
			
			return "pasakumaKomentars/pasakumaKomentars-add-page";

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}

	@PostMapping("/add")
	public String postPasakumaKomentarsInsert( @Valid PasakumaKomentars pasakumaKomentars, BindingResult result, Model model)
			throws Exception {

		if (result.hasErrors()) {
			return "pasakumaKomentars/pasakumaKomentars-add-page";
		} else {
			try {
				//pasakumaKomentars.setPasakums(pasakumsRepo.selectPaskumsById(pasakumsId));
				pasakumaKomentarsRepo.create(pasakumaKomentars);
				return "redirect:/pasakuma-komentars/show/all";
			} catch (Exception e) {
				model.addAttribute("message", e.getMessage());
	            return "error-page";
			}	
		}

	}
	*/
	
	@GetMapping("/add/{pasakumsId}")
	public String getPasakumaKomentarsInsert(@PathVariable("pasakumsId") int pasakumsId, Model model) {

		try {
			Pasakums pasakums = pasakumsRepo.retrieveById(pasakumsId);
			model.addAttribute("pasakumaKomentars", new PasakumaKomentars());
			model.addAttribute("pasakumsId", pasakumsId);
			return "pasakumaKomentars/pasakumaKomentars-add-page";

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}
	
	
	@PostMapping("/add/{pasakumsId}")
	public String postPasakumaKomentarsInsert(@PathVariable("pasakumsId") int pasakumsId, @Valid PasakumaKomentars pasakumaKomentars, BindingResult result, Model model) throws Exception {
		if (result.hasErrors()) {
			return "pasakumaKomentars/pasakumaKomentars-add-page";
		}
		
		try {
			Pasakums pasakums = pasakumsRepo.retrieveById(pasakumsId);
			pasakumaKomentars.setPasakums(pasakums);
			pasakumaKomentarsRepo.create(pasakumaKomentars);
			return "redirect:/pasakuma-komentars/show/all";
			
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
            return "error-page";
		}
				
	}
	
	

	@GetMapping("/update/{id}")
	public String getPasakumaKomentarsUpdateById(@PathVariable("id") int id, Model model) {
		try {
			PasakumaKomentars pasakumaKomentarsForUpdating = pasakumaKomentarsRepo.retrieveById(id);
			model.addAttribute("pasakumaKomentars", pasakumaKomentarsForUpdating);
			model.addAttribute("id", id);
			return "pasakumaKomentars/pasakumaKomentars-update-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}

	@PostMapping("/update/{id}")
	public String postAtlaideUpdateById(@PathVariable("id") int id, @Valid PasakumaKomentars pasakumaKomentars,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return "pasakumaKomentars/pasakumaKomentars-update-page";
			}
			pasakumaKomentarsRepo.update(id, pasakumaKomentars);
			return "redirect:/pasakuma-komentars/show/all/" + id;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}

}
