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
import lv.wings.model.PasakumaBilde;
import lv.wings.service.IAtlaideService;
import lv.wings.service.IPasakumaBildeService;

@Controller
@RequestMapping("/pasakuma-bilde")
public class PasakumaBildeController {
	@Autowired
	private IPasakumaBildeService pasakumaBildeRepo;
	
	
	@GetMapping("/show/all")
	public String getAllPasakumaBilde(Model model) {
		try {
			ArrayList<PasakumaBilde> allPasakumaBilde = pasakumaBildeRepo.retrieveAll();
			model.addAttribute("pasakumaBilde",allPasakumaBilde);
			model.addAttribute("msg", "Visas pasākuma bildes");
			return "pasakumaBilde/pasakumaBilde-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}
	
	

	@GetMapping("/show/all/{id}")
	public String getPasakumaById(@PathVariable("id") int id, Model model) {
		try {
			PasakumaBilde selectedPasakumaBilde = pasakumaBildeRepo.retrieveById(id);
			model.addAttribute("pasakumaBilde",selectedPasakumaBilde);
			model.addAttribute("msg", "Pasākuma bilde pēc id");
			return "pasakumaBilde/pasakumaBilde-all-page";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/remove/{id}")
	public String getDeletePasakumaBildeById(@PathVariable("id") int id, Model model) {
		try {
			pasakumaBildeRepo.deleteById(id);
			return "redirect:/pasakuma-bilde/show/all";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/add")
	public String getPasakumaBildeInsert(Model model) {
		model.addAttribute("pasakumaBilde", new PasakumaBilde());
		return "pasakumaBilde/pasakumaBilde-add-page";
	} 
	
	@PostMapping("/add")
	public String postPasakumaBildeInsert(@Valid PasakumaBilde pasakumaBilde, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			return "pasakumaBilde/pasakumaBilde-add-page";
		} else {
			pasakumaBildeRepo.create(pasakumaBilde);
			return "redirect:/pasakuma-bilde/show/all";
		}
	}
	
	@GetMapping("/update/{id}") 
	public String getPasakumaBildeUpdateById(@PathVariable("id") int id, Model model) {
		try {
			PasakumaBilde pasakumaBildeForUpdating = pasakumaBildeRepo.retrieveById(id);
			model.addAttribute("pasakumaBilde", pasakumaBildeForUpdating);
			model.addAttribute("id", id);
			return "pasakumaBilde/pasakumaBilde-update-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/update/{id}") 
	public String postPasakumaBildeUpdateById(@PathVariable("id") int id,@Valid PasakumaBilde pasakumaBilde, BindingResult result, Model model) {
		try {
			if(result.hasErrors()) {
				return "pasakumaBilde/pasakumaBilde-update-page";
			}
			pasakumaBildeRepo.update(id, pasakumaBilde);
			return "redirect:/pasakuma-bilde/show/all/"+ id;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
		
	}

}
