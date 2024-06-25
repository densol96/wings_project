package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
