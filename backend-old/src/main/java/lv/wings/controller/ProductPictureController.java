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
import lv.wings.model.ProductPicture;
import lv.wings.service.ICRUDInsertedService;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/preces/bilde")
public class ProductPictureController {
	
	@Autowired
	private ICRUDService<ProductPicture> productPictureService;
	

	/* 
	@GetMapping("show/all") //localhost:8080/preces/bilde/show/all
	public String getAllProductPictures(Model model) {
		try {
			ArrayList<ProductPicture> allProductPictures = productPictureService.retrieveAll();
			model.addAttribute("mydata",allProductPictures);
			model.addAttribute("msg","Visas preču bildes");
			return "preces-bilde-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "msg-error-page";
		}
	}
	

	@GetMapping("/show/all/{id}")//localhost:8080/preces/bilde/show/all/{id}
	public String getProductPictureById(@PathVariable("id") int id, Model model) {
		try {
			ProductPicture selectedProductPicture = productPictureService.retrieveById(id);
			model.addAttribute("mydata",selectedProductPicture);
			model.addAttribute("msg", "Preces bilde izvēlēta pēc id");
			return "preces-bilde-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "msg-error-page";
		}
	}
	
	@GetMapping("/remove/{id}") //localhost:8080/preces/bilde/remove/{id}
	public String getDeleteProductPictureById(@PathVariable("id") int id, Model model) {	
		try {
			productPictureService.deleteById(id);
			ArrayList<ProductPicture> allProductPictures = productPictureService.retrieveAll(); 
			model.addAttribute("mydata", allProductPictures);
			model.addAttribute("msg", "Visas preču bildes izņemot izdzēsto pēc id: " + id);
			return "preces-bilde-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
		
	}
	
	@GetMapping("/add/{precesid}") //localhost:8080/preces/bilde/add/{id}
	public String getInsertProductPicture(@PathVariable("precesid") int productId ,Model model) {
		try {
			model.addAttribute("newBilde", new ProductPicture());
			model.addAttribute("precesid", productId);
			return "preces-bilde-add-page";
		}catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "msg-error-page"; 
        }
	} 
	
	@PostMapping("/add/{precesid}")
	public String postInsertProductPicture(@PathVariable("precesid") int productId,
			@Valid ProductPicture productPicture, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "preces-bilde-add-page";
		} else {
			try {
				productPictureService.create(productPicture,productId);
				return "redirect:/preces/bilde/show/all/"+productPicture.getProductPictureId();
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
	            return "msg-error-page";
			}	
		}
	}
	
	@GetMapping("/update/{id}") 
	public String getUpdateProductPictureById(@PathVariable("id") int id, Model model) {
		try {
			ProductPicture productPictureForUpdating = productPictureService.retrieveById(id);
			model.addAttribute("bilde",productPictureForUpdating);
			model.addAttribute("id", id);
			return "preces-bilde-update-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
		
	}
	
	@PostMapping("/update/{id}")
	public String postPrecesBildeUpdateById(@PathVariable("id") int id, 
			@Valid ProductPicture productPicture, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "preces-bilde-update-page";
		}else {
			try {
				productPictureService.update(id, productPicture);
				return "redirect:/preces/bilde/show/all/"+ id;
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "msg-error-page";
			}
		}
	}

	*/
}
