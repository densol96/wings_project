package lv.wings.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.PaymentType;
import lv.wings.model.entity.Purchase;
import lv.wings.poi.PoiController;
import lv.wings.responses.ApiResponse;
import lv.wings.service.CRUDService;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/pirkums")
public class PurchaseController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private CRUDService<Purchase, Integer> purchaseService;


    @GetMapping("/download/all/{id}") // localhost:8080/pirkums/download/all/{id}
    public ResponseEntity<byte[]> downloadPurchaseById(@PathVariable("id") int id) {
        try {
            Purchase purchase = purchaseService.findById(id);
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
            Purchase purchase = purchaseService.findById(id);
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
}
