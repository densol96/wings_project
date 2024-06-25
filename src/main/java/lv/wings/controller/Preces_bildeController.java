package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.wings.model.Preces_bilde;
import lv.wings.service.IPreces_bildeService;

@Controller
@RequestMapping("/preces/bilde")
public class Preces_bildeController {
	
	@Autowired
	private IPreces_bildeService bildeService;
	
	@GetMapping("show/all") //localhost:8080/preces/bilde/show/all
	public String getAllPrecesBildes(Model model) {
		try {
			ArrayList<Preces_bilde> allBildes = bildeService.retrieveAll();
			model.addAttribute("mydata",allBildes);
			model.addAttribute("msg","Visas preču bildes");
			return "preces-bilde-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "error-page";
		}
	}
	
	//TODO: Parādīt error message, ja ir ārous indeksu range
	@GetMapping("/show/all/{id}")//localhost:8080/preces/bilde/show/all/{id}
	public String getPrecesBildeById(@PathVariable("id") int id, Model model) {
		try {
			Preces_bilde selectedPrecesBilde = bildeService.retrieveById(id);
			model.addAttribute("mydata",selectedPrecesBilde);
			model.addAttribute("msg", "Preces bilde izvēlēta pēc id");
			return "preces-bilde-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/remove/{id}") //localhost:8080/preces/bilde/remove/{id}
	public String getPrecesBildeDeleteById(@PathVariable("id") int id, Model model) {	
		try {
			bildeService.deleteById(id);
			ArrayList<Preces_bilde> allBildes = bildeService.retrieveAll(); 
			model.addAttribute("mydata", allBildes);
			model.addAttribute("msg", "Visas preču bildes izņemot izdzēsto pēc id: " + id);
			return "preces-bilde-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
		
	}

}
