package lv.wings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/par-biedribu")
public class AboutController {
	
	@GetMapping("")
	public String aboutPage() {
		return "about-page";
	}

}
