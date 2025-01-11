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
import lv.wings.model.DeliveryType;
import lv.wings.service.ICRUDService;


@Controller
@RequestMapping("/piegades/veids")
public class DeliveryTypeController {
    
    @Autowired
    private ICRUDService<DeliveryType> deliveryTypeService;


    @GetMapping("/show/all")
    public String getShowAllDeliveryTypes(Model model){
        try {
            ArrayList<DeliveryType> allDeliveryTypes = deliveryTypeService.retrieveAll();
            model.addAttribute("mydata", allDeliveryTypes);
            return "pv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOneDeliveryType(@PathVariable("id") int id, Model model){
        try {
            DeliveryType deliveryType = deliveryTypeService.retrieveById(id);
            model.addAttribute("mydata", deliveryType);
            return "pv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{id}")
    public String getDeleteOneDeliveryType(@PathVariable("id") int id, Model model){
        try {
            deliveryTypeService.deleteById(id);
            ArrayList<DeliveryType> allDeliveryTypes = deliveryTypeService.retrieveAll();
            model.addAttribute("mydata", allDeliveryTypes);
            return "pv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddDeliveryType(Model model) {
        model.addAttribute("pv", new DeliveryType());
        return "pv-add-page";
    }


    @PostMapping("/add")
    public String postAddDeliveryType(@Valid DeliveryType deliveryType, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pv-add-page";
        } else {
            try {
                deliveryTypeService.create(deliveryType);
                return "redirect:/piegades/veids/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }

    
    @GetMapping("/update/{id}")
    public String getUpdateDeliveryType(@PathVariable("id") int id, Model model) {
        try {
            DeliveryType deliveryTypeToUpdate = deliveryTypeService.retrieveById(id);
            model.addAttribute("pv", deliveryTypeToUpdate);
            model.addAttribute("id", id);
            return "pv-update-page";
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @PostMapping("/update/{id}")
    public String postUpdateDeliveryType(@PathVariable("id") int id, @Valid DeliveryType deliveryType, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pv-update-page";
        } else {
            try {
                deliveryTypeService.update(id, deliveryType);
                return "redirect:/piegades/veids/show/all/" + id;
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }
}
