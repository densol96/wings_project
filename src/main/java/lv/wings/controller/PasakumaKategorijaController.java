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
import lv.wings.model.Atlaide;
import lv.wings.model.PasakumaKategorija;
import lv.wings.repo.IPasakumaKategorija;
import lv.wings.service.IAtlaideService;
import lv.wings.service.IPasakumaKategorijaService;

@Controller
@RequestMapping("/pasakuma-kategorija")
public class PasakumaKategorijaController {
	
	@Autowired
	private IPasakumaKategorijaService pasakumaKategorijaRepo;
	
	
	@GetMapping("/show/all")
	public String getAllPasakumaKategorija(Model model) {
		try {
			ArrayList<PasakumaKategorija> allPasakumaKategorija = pasakumaKategorijaRepo.retrieveAll();
			model.addAttribute("pasakumaKategorija",allPasakumaKategorija);
			model.addAttribute("msg", "Visas pasākuma kategorijas");
			return "pasakumaKategorija/pasakumaKategorija-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}
	
	

	@GetMapping("/show/all/{id}")
	public String getPasakumaKategorijaById(@PathVariable("id") int id, Model model) {
		try {
	    PasakumaKategorija selectedPasakumaKategorija = pasakumaKategorijaRepo.retrieveById(id);
			model.addAttribute("pasakumaKategorija",selectedPasakumaKategorija);
			model.addAttribute("msg", "Pasākuma kategorija pēc id");
			return "pasakumaKategorija/pasakumaKategorija-all-page";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/remove/{id}")
	public String getDeletePasakumaKategorijaById(@PathVariable("id") int id, Model model) {
		try {
			pasakumaKategorijaRepo.deleteById(id);
			return "redirect:/pasakuma-kategorija/show/all";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/add")
	public String getPasakumaKategorijaInsert(Model model) {
		model.addAttribute("pasakumaKategorija", new PasakumaKategorija());
		return "pasakumaKategorija/pasakumaKategorija-add-page";
	} 
	
	@PostMapping("/add")
	public String postPasakumaKategorijaInsert(@Valid PasakumaKategorija pasakumaKategorija, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			return "pasakumaKategorija/pasakumaKategorija-add-page";
		} else {
			pasakumaKategorijaRepo.create(pasakumaKategorija);
			return "redirect:/pasakuma-kategorija/show/all";
		}
	}
	
	@GetMapping("/update/{id}") 
	public String getPasakumaKategorijaUpdateById(@PathVariable("id") int id, Model model) {
		try {
			PasakumaKategorija pasakumaKategorijaForUpdating = pasakumaKategorijaRepo.retrieveById(id);
			model.addAttribute("pasakumaKategorija",pasakumaKategorijaForUpdating);
			model.addAttribute("id", id);
			return "pasakumaKategorija/pasakumaKategorija-update-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/update/{id}") 
	public String postPasakumaKategorijaUpdateById(@PathVariable("id") int id,@Valid PasakumaKategorija pasakumaKategorija, BindingResult result, Model model) {
		try {
			if(result.hasErrors()) {
				return "atlaide/atlaide-update-page";
			}
			pasakumaKategorijaRepo.update(id, pasakumaKategorija);
			return "redirect:/pasakuma-kategorija/show/all/"+ id;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
		
	}

}
