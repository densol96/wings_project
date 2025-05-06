package lv.wings.controller.open;

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
// import lv.wings.poi.PoiController;
import lv.wings.service.CRUDService;
import lv.wings.service.ICRUDService;

// @Controller
// @RequestMapping("/customer")
// public class CustomerController {

// @Autowired
// private CRUDService<Customer, Integer> customerService;

// @GetMapping("/download/all") // localhost:8080/pircejs/download/all
// public ResponseEntity<byte[]> downloadCustomers() {
// try {
// // List<Customer> allCustomers = customerService.retrieveAll();
// List<Customer> allCustomers = null;
// byte[] fileBytes = PoiController.buildMultiple("pirceji", (ArrayList<Customer>) allCustomers,
// new String[] {});

// HttpHeaders headers = new HttpHeaders();
// headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
// headers.setContentDispositionFormData("attachment", "query_results.xlsx");

// return ResponseEntity.ok().headers(headers).body(fileBytes);
// } catch (Exception e) {
// return ResponseEntity.notFound().build();
// }
// }

// }
