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
	
	@GetMapping("/add/{kategorijasid}") //localhost:8080/prece/add/{id}
	public String getPreceInsert(@PathVariable("kategorijasid") int kategorijasID ,Model model) {
		try {
			model.addAttribute("newPrece", new Prece());
			model.addAttribute("kategorijasid", kategorijasID);
			return "prece-add-page";
		}catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page"; 
        }
	} 
	
	@PostMapping("/add/{kategorijasid}")
	public String postPreceInsert(@PathVariable("kategorijasid") int kategorijasID,
			@Valid Prece prece, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "prece-add-page";
		} else {
			try {
				preceService.create(prece,kategorijasID);
				return "redirect:/prece/show/all/"+prece.getPrece_id();
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
	            return "error-page";
			}	
		}
	}
	
	//TODO: pielikt kategorijām drop down list
	@GetMapping("/update/{id}") //localhost:8080/prece/update/{id}
	public String getPreceUpdateById(@PathVariable("id") int id, Model model) {
		try {
			Prece preceForUpdating = preceService.retrieveById(id);
			model.addAttribute("prece",preceForUpdating);
			model.addAttribute("id", id);
			return "prece-update-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/update/{id}")
	public String postPreceUpdateById(@PathVariable("id") int id, 
			@Valid Prece prece, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "prece-update-page";
		}else {
			try {
				preceService.update(id, prece);
				return "redirect:/prece/show/all/"+ id;
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "error-page";
			}
		}
	}

}
