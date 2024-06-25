package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.wings.model.Pirkuma_elements;
import lv.wings.service.IPirkuma_elementsService;

@Controller
@RequestMapping("/pirkuma/elements")
public class Pirkuma_elementsController {

	@Autowired
	private IPirkuma_elementsService elementsService;
	
	@GetMapping("/show/all")//localhost:8080/pirkuma/elements/show/all
	public String getAllPirkumaElementi(Model model) {
		try {
			ArrayList<Pirkuma_elements> allPirkumaElementi = elementsService.retrieveAll();
			model.addAttribute("mydata",allPirkumaElementi);
			model.addAttribute("msg", "Visi pirkuma elementi");
			return "elementi-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/show/all/{id}")//localhost:8080/pirkuma/elements/show/all/{id}
	public String getPirkumaElementsById(@PathVariable("id") int id, Model model) {
		try {
			Pirkuma_elements selectedElements = elementsService.retrieveById(id);
			model.addAttribute("mydata",selectedElements);
			model.addAttribute("msg", "Pirkuma elements izvēlēts pēc id");
			return "elementi-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/remove/{id}") //localhost:8080/pirkuma/elements/remove/{id}
	public String getPrecesElementsById(@PathVariable("id") int id, Model model) {	
		try {
			elementsService.deleteById(id);
			ArrayList<Pirkuma_elements> allPirkumaElementi = elementsService.retrieveAll(); 
			model.addAttribute("mydata", allPirkumaElementi);
			model.addAttribute("msg", "Visi pirkuma elementi izņemot izdzēsto pēc id: " + id);
			return "elementi-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
		
	}
	
}
