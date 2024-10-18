package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.wings.model.Product;
import lv.wings.poi.PoiController;
import lv.wings.service.ICRUDInsertedService;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/prece")
public class ProductController {
	
	@Autowired
	private ICRUDInsertedService<Product> productService;
	
	@GetMapping("/show/all")//localhost:8080/prece/show/all
	public String getAllProducts(Model model) {
		try {
			ArrayList<Product> allProducts = productService.retrieveAll();
			model.addAttribute("mydata",allProducts);
			model.addAttribute("msg", "Visas preces");
			return "preces-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
	}
	
	@GetMapping("/show/all/{id}")//localhost:8080/prece/show/all/{id}
	public String getProductById(@PathVariable("id") int id, Model model) {
		try {
			Product selectedProduct = productService.retrieveById(id);
			model.addAttribute("mydata",selectedProduct);
			model.addAttribute("msg", "Preces izvēlēta pēc id");
			return "preces-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata",e.getMessage());
			return "msg-error-page";
		}
	}

	@GetMapping("/download/all")//localhost:8080/prece/download/all
	public ResponseEntity<byte[]> downloadProducts() {
		try {
			ArrayList<Product> allProducts = productService.retrieveAll();
			//iegūt faila baitus no preces ar visiem pieejamiem laukiem
			byte[] fileBytes = PoiController.buildMultiple("preces", allProducts, new String[]{});

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "query_results.xlsx");

			return ResponseEntity.ok().headers(headers).body(fileBytes);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/download/all/{id}")//localhost:8080/prece/download/all/{id}
	public ResponseEntity<byte[]> downloadProductsById(@PathVariable("id") int id) {
		try {
			Product selectedProduct = productService.retrieveById(id);
			//iegūt faila baitus no preces ar definētiem laukiem
			byte[] fileBytes = PoiController.buildSingle("prece-"+id, selectedProduct, new String[]{"prece_id", "nosaukums", "cena", "daudzums"});

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "query_results.xlsx");

			return ResponseEntity.ok().headers(headers).body(fileBytes);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/remove/{id}") //localhost:8080/prece/remove/{id}
	public String getDeleteProductById(@PathVariable("id") int id, Model model) {	
		try {
			productService.deleteById(id);
			ArrayList<Product> allProducts = productService.retrieveAll(); 
			model.addAttribute("mydata", allProducts);
			model.addAttribute("msg", "Visas preces izņemot izdzēsto pēc id: " + id);
			return "preces-all-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
	}
	
	@GetMapping("/add/{kategorijasid}") //localhost:8080/prece/add/{id}
	public String getInsertProduct(@PathVariable("kategorijasid") int id ,Model model) {
		try {
			model.addAttribute("newPrece", new Product());
			model.addAttribute("kategorijasid", id);
			return "prece-add-page";
		}catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "msg-error-page"; 
        }
	} 
	
	@PostMapping("/add/{kategorijasid}")
	public String postInsertProduct(@PathVariable("kategorijasid") int categoryId,
			@Valid Product product, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "prece-add-page";
		} else {
			try {
				productService.create(product,categoryId);
				return "redirect:/prece/show/all/"+product.getProductId();
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
	            return "msg-error-page";
			}	
		}
	}
	
	//TODO: pielikt kategorijām drop down list
	@GetMapping("/update/{id}") //localhost:8080/prece/update/{id}
	public String getUpdateProductById(@PathVariable("id") int id, Model model) {
		try {
			Product productForUpdating = productService.retrieveById(id);
			model.addAttribute("prece",productForUpdating);
			model.addAttribute("id", id);
			return "prece-update-page";
		} catch (Exception e) {
			model.addAttribute("mydata", e.getMessage());
			return "msg-error-page";
		}
		
	}
	
	@PostMapping("/update/{id}")
	public String postUpdateProductById(@PathVariable("id") int id, 
			@Valid Product product, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "prece-update-page";
		}else {
			try {
				productService.update(id, product);
				return "redirect:/prece/show/all/"+ id;
			} catch (Exception e) {
				model.addAttribute("mydata", e.getMessage());
				return "msg-error-page";
			}
		}
	}

}
