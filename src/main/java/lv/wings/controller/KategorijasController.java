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
import lv.wings.model.Kategorijas;
import lv.wings.service.IKategorijasService;

@Controller
@RequestMapping("/kategorijas")
public class KategorijasController {
	
	@Autowired
	private IKategorijasService kategorijasService;
	
	@GetMapping("/show/all") //localhost:8080/kategorijas/show/all
	public String getAllKategorijas(Model model) {
		try {
			ArrayList<Kategorijas> allKategorijas = kategorijasService.retrieveAll();
			model.addAttribute("mydata", allKategorijas); //padod izfiltrētās kategorijas uz katergorijas-all-page.html
			model.addAttribute("msg","Visas kategorijas");
			return "kategorijas-page";
		}catch(Exception e) {
			model.addAttribute("mydata", e.getMessage());//padod kludas zinu uz error-page.html lapu
			return "error-page";
		}
	}
	

	//TODO:Jāsakārtot error-page.html
	@GetMapping("/show/all/{id}")//localhost:8080/kategorijas/show/all/{id}
	public String getKategorijasById(@PathVariable("id") int id, Model model) {
		try {
			Kategorijas selectedKategorijas = kategorijasService.retrieveById(id);
			model.addAttribute("mydata",selectedKategorijas);
			model.addAttribute("msg", "Kategorijas izvēlētas pēc id");
			return "kategorijas-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "error-page";
		}
	}
	
	//TODO: Neļauj izdzēst, ja ir sasaistīt foreigh key. Vajadzētu parādīt error message
	@GetMapping("/remove/{id}") //localhost:8080/kategorijas/remove/{id}
	public String getKategorijasDeleteById(@PathVariable("id") int id, Model model) {	
		try {
			kategorijasService.deleteById(id);
			ArrayList<Kategorijas> allKategorijas = kategorijasService.retrieveAll(); 
			model.addAttribute("mydata", allKategorijas);
			model.addAttribute("msg", "Visas kategorijas izņemot izdzēsto pēc id: " + id);
			return "kategorijas-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
		
	}
	
	@GetMapping("/add") //localhost:8080/kategorijas/add
	public String getKategorijasInsert(Model model) {
		model.addAttribute("kategorija", new Kategorijas());
		return "kategorijas-add-page";
		
	} 
	
	@PostMapping("/add")
	public String postKategorijasInsert(@Valid Kategorijas kategorija, BindingResult result) throws Exception {
		// vai ir kādi validācijas pāŗkāpumi
		if (result.hasErrors()) {
			return "kategorijas-add-page";
		} else {
			kategorijasService.create(kategorija);
			return "redirect:/kategorijas/show/all";
		}

	}
	

}
