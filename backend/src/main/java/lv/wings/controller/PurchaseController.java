package lv.wings.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
import lv.wings.mail.MailSender;
import lv.wings.model.Customer;
import lv.wings.model.DeliveryType;
import lv.wings.model.PaymentType;
import lv.wings.model.Purchase;
import lv.wings.poi.PoiController;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/pirkums")
public class PurchaseController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ICRUDService<Purchase> purchaseService;

    @Autowired
    private ICRUDService<DeliveryType> deliveryTypeService;

    @Autowired
    private ICRUDService<PaymentType> paymentTypeService;

    @Autowired
    private ICRUDService<Customer> customerService;

    @GetMapping("/show/all")
    public String getShowAllPurchases(Model model) {
        try {
            ArrayList<Purchase> allPurchases = purchaseService.retrieveAll();
            model.addAttribute("mydata", allPurchases);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/show/all/{id}")
    public String getShowOnePurchase(@PathVariable("id") int id, Model model) {
        try {
            Purchase purchase = purchaseService.retrieveById(id);
            model.addAttribute("mydata", purchase);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/download/all/{id}") // localhost:8080/pirkums/download/all/{id}
    public ResponseEntity<byte[]> downloadPurchaseById(@PathVariable("id") int id) {
        try {
            Purchase purchase = purchaseService.retrieveById(id);
            // iegūt faila baitus no preces ar definētiem laukiem
            byte[] fileBytes = PoiController.buildInvoice("pirkums-" + id, purchase);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "invoice-" + id + ".docx");

            return ResponseEntity.ok().headers(headers).body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/purchace/{id}") // localhost:8080/pirkums/purchace/{id}
    public ResponseEntity<ApiResponse<Boolean>> performPurchaseById(@PathVariable("id") int id) {
        try {
            System.out.println("smth");
            Purchase purchase = purchaseService.retrieveById(id);
            System.out.println(purchase.getDeliveryDetails());

            // iegūt faila baitus no preces ar definētiem laukiem
            byte[] fileBytes = PoiController.buildInvoice("pirkums-" + id, purchase);

            mailSender.sendMessage(/* purchase.getCustomer().getEmail() */mailSender.getDestinationEmail(), "Delivery",
                    "some contents", "invoice-" + id + ".docx", new ByteArrayResource(fileBytes));
            return ResponseEntity.ok().body(new ApiResponse<>(null, true));
        } catch (Exception e) {
            System.out.println("rip");
            return ResponseEntity.ok().body(new ApiResponse<>(null, false));
        }
    }

    @GetMapping("/remove/{id}")
    public String getDeleteOnePurchase(@PathVariable("id") int id, Model model) {
        try {
            purchaseService.deleteById(id);
            ArrayList<Purchase> allPurchases = purchaseService.retrieveAll();
            model.addAttribute("mydata", allPurchases);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/add")
    public String getAddPurchase(Model model) {
        try {
            ArrayList<PaymentType> paymentTypes = paymentTypeService.retrieveAll();
            ArrayList<DeliveryType> deliveryTypes = deliveryTypeService.retrieveAll();
            ArrayList<Customer> customers = customerService.retrieveAll();
            model.addAttribute("pirkums", new Purchase());
            model.addAttribute("samaksasVeidi", paymentTypes);
            model.addAttribute("piegadesVeidi", deliveryTypes);
            model.addAttribute("pirceji", customers);
            model.addAttribute("pasutijumaDatums", LocalDateTime.now());
            return "pirkums-add-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/add")
    public String postAddPurchase(@Valid Purchase purchase, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(purchase.getDeliveryType());
            System.out.println(purchase.getPaymentType());
            System.out.println(purchase.getCustomer());
            System.out.println(purchase.getDeliveryDetails());
            System.out.println(purchase.getDeliveryDate());
            return "pirkums-add-page";
        } else {
            try {
                System.out.println("aaaab");
                purchaseService.create(purchase);
                return "redirect:/pirkums/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }
}
