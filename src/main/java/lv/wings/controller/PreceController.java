package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.wings.model.Prece;
import lv.wings.service.IPreceService;

@Controller
@RequestMapping("/prece")
public class PreceController {
	
	@Autowired
	private IPreceService preceService;
	
	@GetMapping("/show/all")//localhost:8080/prece/show/all
	public String getAllPreces(Model model) {
		try {
			ArrayList<Prece> allPreces = preceService.retrieveAll();
			model.addAttribute("mydata",allPreces);
			model.addAttribute("msg", "Visas preces");
			return "preces-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/show/all/{id}")//localhost:8080/prece/show/all/{id}
	public String getPrecesById(@PathVariable("id") int id, Model model) {
		try {
			Prece selectedPrece = preceService.retrieveById(id);
			model.addAttribute("mydata",selectedPrece);
			model.addAttribute("msg", "Preces izvēlēta pēc id");
			return "preces-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "error-page";
		}
	}
	
	//TODO: SQL kļūda, tā pati, kas kategorijām
	@GetMapping("/remove/{id}") //localhost:8080/prece/remove/{id}
	public String getPreceDeleteById(@PathVariable("id") int id, Model model) {	
		try {
			preceService.deleteById(id);
			ArrayList<Prece> allPreces = preceService.retrieveAll(); 
			model.addAttribute("mydata", allPreces);
			model.addAttribute("msg", "Visas preces izņemot izdzēsto pēc id: " + id);
			return "preces-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
		
	}

}
