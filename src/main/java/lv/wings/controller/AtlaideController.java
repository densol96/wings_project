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
import lv.wings.service.ICRUDService;


@Controller
@RequestMapping("/atlaide")
public class AtlaideController {
	
	@Autowired
	private ICRUDService<Atlaide> atlaidesRepo;


	@GetMapping(value = "/show/all", produces = "application/json")
	public ArrayList<Atlaide> getAtlaides() {
		ArrayList<Atlaide> atlaides = new ArrayList<>();
		Atlaide at1 = new Atlaide();
		Atlaide at2 = new Atlaide();
		Atlaide at3 = new Atlaide();
		atlaides.add(at1);
		atlaides.add(at2);
		atlaides.add(at3);
		
		return atlaides;
	}
	
	
	
	 
	@GetMapping("/show/all")
	public String getAllAtlaides(Model model) {
		try {
			ArrayList<Atlaide> allAtlaides = atlaidesRepo.retrieveAll();
			model.addAttribute("atlaides",allAtlaides);
			model.addAttribute("msg", "Visas atlaides");
			return "atlaide/atlaide-all-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
	}
	
	
	@GetMapping("/show/all/{id}")
	public String getAtlaideById(@PathVariable("id") int id, Model model) {
		try {
			Atlaide selectedAtlaide = atlaidesRepo.retrieveById(id);
			model.addAttribute("atlaides",selectedAtlaide);
			model.addAttribute("msg", "Atlaide pēc id");
			return "atlaide/atlaide-all-page";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/remove/{id}")
	public String getDeleteAtlaideById(@PathVariable("id") int id, Model model) {
		try {
			atlaidesRepo.deleteById(id);
			return "redirect:/atlaide/show/all";
		} catch (Exception e) {
			model.addAttribute("message",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/add")
	public String getAtlaideInsert(Model model) {
		model.addAttribute("atlaide", new Atlaide());
		return "atlaide/atlaide-add-page";
	} 
	
	@PostMapping("/add")
	public String postPasakumaBildeInsert(@Valid Atlaide atlaide, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			return "atlaide/atlaide-add-page";
		} else {
			atlaidesRepo.create(atlaide);
			return "redirect:/atlaide/show/all";
		}
	}
	
	@GetMapping("/update/{id}") 
	public String getAtlaideUpdateById(@PathVariable("id") int id, Model model) {
		try {
			Atlaide atlaideForUpdating = atlaidesRepo.retrieveById(id);
			model.addAttribute("atlaide",atlaideForUpdating);
			model.addAttribute("id", id);
			return "atlaide/atlaide-update-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/update/{id}") 
	public String postAtlaideUpdateById(@PathVariable("id") int id,@Valid Atlaide atlaide, BindingResult result, Model model) {
		try {
			if(result.hasErrors()) {
				return "atlaide/atlaide-update-page";
			}
			atlaidesRepo.update(id, atlaide);
			return "redirect:/atlaide/show/all/"+ id;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}
		
		
	}
}
