package lv.wings.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.wings.model.PurchaseElement;
import lv.wings.service.ICRUDInsertedService;

@Controller
@RequestMapping("/pirkuma/elements")
public class PurchaseElementController {

	@Autowired
	private ICRUDInsertedService<PurchaseElement> elementService;

	@GetMapping("/show/all") // localhost:8080/pirkuma/elements/show/all
	public String getAllPurchaseElements(Model model) {
		try {
			List<PurchaseElement> allPurchaseElements = elementService.retrieveAll();
			model.addAttribute("mydata", allPurchaseElements);
			model.addAttribute("msg", "Visi pirkuma elementi");
			return "elementi-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
	}

	@GetMapping("/show/all/{id}") // localhost:8080/pirkuma/elements/show/all/{id}
	public String getPurchaseElementById(@PathVariable("id") int id, Model model) {
		try {
			PurchaseElement selectedElement = elementService.retrieveById(id);
			model.addAttribute("mydata", selectedElement);
			model.addAttribute("msg", "Pirkuma elements izvēlēts pēc id");
			return "elementi-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
	}

	@GetMapping("/remove/{id}") // localhost:8080/pirkuma/elements/remove/{id}
	public String getRemovePurchaseElementById(@PathVariable("id") int id, Model model) {
		try {
			elementService.deleteById(id);
			List<PurchaseElement> allPurchaseElements = elementService.retrieveAll();
			model.addAttribute("mydata", allPurchaseElements);
			model.addAttribute("msg", "Visi pirkuma elementi izņemot izdzēsto pēc id: " + id);
			return "elementi-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}

	}

	@GetMapping("/add/{precesid}") // localhost:8080/pirkuma/elements/add/{id}
	public String getInsertPurchaseElement(@PathVariable("precesid") int productId, Model model) {
		try {
			model.addAttribute("newElements", new PurchaseElement());
			model.addAttribute("precesid", productId);
			return "pirkuma-elements-add-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
	}

	@PostMapping("/add/{precesid}")
	public String postInsertPurchaseElement(@PathVariable("precesid") int productId,
			@Valid PurchaseElement element, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "pirkuma-elements-add-page";
		} else {
			try {
				elementService.create(element, productId);
				return "redirect:/pirkuma/elements/show/all/" + element.getPurchaseElementId();
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "msg-error-page";
			}
		}
	}

	@GetMapping("/update/{id}") // localhost:8080/pirkuma/elements/update/{id}
	public String getUpdatePurchaseElementById(@PathVariable("id") int id, Model model) {
		try {
			PurchaseElement elementForUpdating = elementService.retrieveById(id);
			model.addAttribute("elements", elementForUpdating);
			model.addAttribute("id", id);
			return "pirkuma-elements-update-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}

	}

	@PostMapping("/update/{id}")
	public String postUpdatePurchaseElementById(@PathVariable("id") int id,
			@Valid PurchaseElement element, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "pirkuma-elements-update-page";
		} else {
			try {
				elementService.update(id, element);
				return "redirect:/pirkuma/elements/show/all/" + id;
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "msg-error-page";
			}
		}
	}

}
