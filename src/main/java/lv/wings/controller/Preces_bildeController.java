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
	
	@GetMapping("/add/{precesid}") //localhost:8080/preces/bilde/add/{id}
	public String getPrecesBildeInsert(@PathVariable("precesid") int precesID ,Model model) {
		try {
			model.addAttribute("newBilde", new Preces_bilde());
			model.addAttribute("precesid", precesID);
			return "preces-bilde-add-page";
		}catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page"; 
        }
	} 
	
	@PostMapping("/add/{precesid}")
	public String postPrecesBildeInsert(@PathVariable("precesid") int precesID,
			@Valid Preces_bilde bilde, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "preces-bilde-add-page";
		} else {
			try {
				bildeService.create(bilde,precesID);
				return "redirect:/preces/bilde/show/all/"+bilde.getP_b_id();
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
	            return "error-page";
			}	
		}
	}
	
	@GetMapping("/update/{id}") //localhost:8080/preces/bilde/update/{id}
	public String getPrecesBildeUpdateById(@PathVariable("id") int id, Model model) {
		try {
			Preces_bilde bildeForUpdating = bildeService.retrieveById(id);
			model.addAttribute("bilde",bildeForUpdating);
			model.addAttribute("id", id);
			return "preces-bilde-update-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/update/{id}")
	public String postPrecesBildeUpdateById(@PathVariable("id") int id, 
			@Valid Preces_bilde bilde, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "preces-bilde-update-page";
		}else {
			try {
				bildeService.update(id, bilde);
				return "redirect:/preces/bilde/show/all/"+ id;
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "error-page";
			}
		}
	}

}
