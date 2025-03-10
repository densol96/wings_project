package lv.wings.controller;

import java.util.ArrayList;
import java.util.List;

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
import lv.wings.model.entity.Customer;
import lv.wings.poi.PoiController;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICRUDService<Customer> customerService;

    @GetMapping("/show/all")
    public String getShowAllCustomers(Model model) {
        try {
            List<Customer> allCustomers = customerService.retrieveAll();
            model.addAttribute("mydata", allCustomers);
            return "pircejs-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/show/all/{id}")
    public String getShowOneCustomer(@PathVariable("id") int id, Model model) {
        try {
            Customer customer = customerService.retrieveById(id);
            model.addAttribute("mydata", customer);
            return "pircejs-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/download/all") // localhost:8080/pircejs/download/all
    public ResponseEntity<byte[]> downloadCustomers() {
        try {
            List<Customer> allCustomers = customerService.retrieveAll();
            byte[] fileBytes = PoiController.buildMultiple("pirceji", (ArrayList<Customer>) allCustomers,
                    new String[] {});

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "query_results.xlsx");

            return ResponseEntity.ok().headers(headers).body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/remove/{id}")
    public String getDeleteOneCustomer(@PathVariable("id") int id, Model model) {
        try {
            customerService.deleteById(id);
            List<Customer> allCustomers = customerService.retrieveAll();
            model.addAttribute("mydata", allCustomers);
            return "pircejs-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/add")
    public String getAddCustomer(Model model) {
        model.addAttribute("pircejs", new Customer());
        return "pircejs-add-page";
    }

    @PostMapping("/add")
    public String postAddCustomer(@Valid Customer pircejs, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pircejs-add-page";
        } else {
            try {
                customerService.create(pircejs);
                return "redirect:/pircejs/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateCustomer(@PathVariable("id") int id, Model model) {
        try {
            Customer customerToUpdate = customerService.retrieveById(id);
            model.addAttribute("pircejs", customerToUpdate);
            model.addAttribute("id", id);
            return "pircejs-update-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}")
    public String postUpdateCustomer(@PathVariable("id") int id, @Valid Customer customer, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "pircejs-update-page";
        } else {
            try {
                customerService.update(id, customer);
                return "redirect:/pircejs/show/all/" + id;
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }
}
