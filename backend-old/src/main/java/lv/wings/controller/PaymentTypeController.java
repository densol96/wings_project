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
import lv.wings.model.PaymentType;
import lv.wings.service.ICRUDService;

@Controller
@RequestMapping("/samaksas/veids")
public class PaymentTypeController {
    
    @Autowired
    private ICRUDService<PaymentType> paymentTypeService;


    @GetMapping("/show/all")
    public String getShowAllPaymentTypes(Model model){
        try {
            ArrayList<PaymentType> allPaymentTypes = paymentTypeService.retrieveAll();
            model.addAttribute("mydata", allPaymentTypes);
            return "sv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOnePaymentType(@PathVariable("id") int id, Model model){
        try {
            PaymentType paymentType = paymentTypeService.retrieveById(id);
            model.addAttribute("mydata", paymentType);
            return "sv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{id}")
    public String getDeleteOnePaymentType(@PathVariable("id") int id, Model model){
        try {
            paymentTypeService.deleteById(id);
            ArrayList<PaymentType> allPaymentTypes = paymentTypeService.retrieveAll();
            model.addAttribute("mydata", allPaymentTypes);
            return "sv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddPaymentType(Model model) {
        model.addAttribute("sv", new PaymentType());
        return "sv-add-page";
    }


    @PostMapping("/add")
    public String postAddPaymentType(@Valid PaymentType paymentType, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "sv-add-page";
        } else {
            try {
                paymentTypeService.create(paymentType);
                return "redirect:/samaksas/veids/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }

    
    @GetMapping("/update/{id}")
    public String getUpdatePaymentType(@PathVariable("id") int id, Model model) {
        try {
            PaymentType paymentTypeToUpdate = paymentTypeService.retrieveById(id);
            model.addAttribute("sv", paymentTypeToUpdate);
            model.addAttribute("id", id);
            return "sv-update-page";
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @PostMapping("/update/{id}")
    public String postUpdatePaymentType(@PathVariable("id") int id, @Valid PaymentType paymentType, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "sv-update-page";
        } else {
            try {
                paymentTypeService.update(id, paymentType);
                return "redirect:/samaksas/veids/show/all/" + id;
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }


}
