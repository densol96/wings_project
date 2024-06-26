package lv.wings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kontakti")
public class ContactsController {
	
	@GetMapping("")
	public String contactPage() {
		return "contact-page";
	}

}
