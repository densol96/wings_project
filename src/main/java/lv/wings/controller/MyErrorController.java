package lv.wings.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class MyErrorController implements ErrorController {
	@GetMapping
	public String handleError(Model model) {
		model.addAttribute("message", "Page not found!");
		return "error-page";
	}
}
