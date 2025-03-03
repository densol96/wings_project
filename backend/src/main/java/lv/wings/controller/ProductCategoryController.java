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
import lv.wings.model.ProductCategory;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/kategorijas")
public class ProductCategoryController {
	
	@Autowired
	private ICRUDService<ProductCategory> productCategoryService;
	
	@GetMapping("/show/all") //localhost:8080/kategorijas/show/all
	public String getAllProductCategories(Model model) {
		try {
			ArrayList<ProductCategory> allProductCategories = productCategoryService.retrieveAll();
			model.addAttribute("mydata", allProductCategories); //padod izfiltrētās kategorijas uz katergorijas-all-page.html
			model.addAttribute("msg","Visas kategorijas");
			return "kategorijas-page";
		}catch(Exception e) {
			model.addAttribute("mydata", e.getMessage());//padod kludas zinu uz error-page.html lapu
			return "msg-error-page";
		}
	}
	

	@GetMapping("/show/all/{id}")//localhost:8080/kategorijas/show/all/{id}
	public String getProductCategoriesById(@PathVariable("id") int id, Model model) {
		try {
			ProductCategory selectedProductCategory = productCategoryService.retrieveById(id);
			model.addAttribute("mydata",selectedProductCategory);
			model.addAttribute("msg", "Kategorijas izvēlētas pēc id");
			return "kategorijas-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "msg-error-page";
		}
	}
	
	@GetMapping("/remove/{id}") //localhost:8080/kategorijas/remove/{id}
	public String getDeleteProductCategoryById(@PathVariable("id") int id, Model model) {	
		try {
			productCategoryService.deleteById(id);
			ArrayList<ProductCategory> allProductCategories = productCategoryService.retrieveAll(); 
			model.addAttribute("mydata", allProductCategories);
			model.addAttribute("msg", "Visas kategorijas izņemot izdzēsto pēc id: " + id);
			return "kategorijas-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
		
	}
	
	@GetMapping("/add") //localhost:8080/kategorijas/add
	public String getInsertProductCategory(Model model) {
		model.addAttribute("kategorija", new ProductCategory());
		return "kategorijas-add-page";
		
	} 
	
	@PostMapping("/add")
	public String postInsertProductCategory(@Valid ProductCategory productCategory, BindingResult result) throws Exception {
		// vai ir kādi validācijas pāŗkāpumi
		if (result.hasErrors()) {
			return "kategorijas-add-page";
		} else {
			productCategoryService.create(productCategory);
			return "redirect:/kategorijas/show/all";
		}

	}
	
	@GetMapping("/update/{id}") //localhost:8080/kategorijas/update/{id}
	public String getUpdateProductCategoryById(@PathVariable("id") int id, Model model) {
		try {
			ProductCategory productCategoryForUpdating = productCategoryService.retrieveById(id);
			model.addAttribute("kategorija",productCategoryForUpdating);
			model.addAttribute("id", id);
			return "kategorija-update-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
		
	}
	
	@PostMapping("/update/{id}")
	public String postUpdateProductCategoryById(@PathVariable("id") int id, 
			@Valid ProductCategory productCategory, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "kategorija-update-page";
		}else {
			try {
				productCategoryService.update(id, productCategory);
				return "redirect:/kategorijas/show/all/"+ id;
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "msg-error-page";
			}
		}
	}
	
	
	

}
