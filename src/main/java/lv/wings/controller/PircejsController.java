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
import lv.wings.model.Pircejs;
import lv.wings.service.IPircejsService;


@Controller
@RequestMapping("/pircejs")
public class PircejsController {
    
    @Autowired
    private IPircejsService pircejsService;


    @GetMapping("/show/all")
    public String getShowAllPircejs(Model model){
        try {
            ArrayList<Pircejs> allpircejs = pircejsService.selectAllPircejs();
            model.addAttribute("mydata", allpircejs);
            return "pircejs-all-page";
        } catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOnePircejs(@PathVariable("id") int id, Model model){
        try {
            Pircejs pircejs = pircejsService.selectPircejsById(id);
            model.addAttribute("mydata", pircejs);
            return "pircejs-one-page";
        } catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{id}")
    public String getDeleteOnePircejs(@PathVariable("id") int id, Model model){
        try {
            pircejsService.deletePircejsById(id);
            ArrayList<Pircejs> allpircejs = pircejsService.selectAllPircejs();
            model.addAttribute("mydata", allpircejs);
            return "pircejs-all-page";
        } catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddPircejs(Model model) {
        model.addAttribute("pircejs", new Pircejs());
        return "pircejs-add-page";
    }


    @PostMapping("/add")
    public String postAddPircejs(@Valid Pircejs pircejs, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pircejs-add-page";
        } else {
            try {
                pircejsService.insertNewPircejs(pircejs);
                return "redirect:/pircejs/show/all";
            } catch (Exception e) {
                model.addAttribute("mydata", e.getMessage());
                return "error-page";
            }
        }
    }

    
    @GetMapping("/update/{id}")
    public String getUpdatePircejs(@PathVariable("id") int id, Model model) {
        try {
            Pircejs pircejsToUpdate = pircejsService.selectPircejsById(id);
            model.addAttribute("pircejs", pircejsToUpdate);
            model.addAttribute("id", id);
            return "pircejs-update-page";
        } catch (Exception e){
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @PostMapping("/update/{id}")
    public String postUpdatePircejs(@PathVariable("id") int id, @Valid Pircejs pircejs, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pircejs-update-page";
        } else {
            try {
                pircejsService.updatePircejsById(id, pircejs);
                return "redirect:/pircejs/show/all/" + id;
            } catch (Exception e) {
                model.addAttribute("mydata", e.getMessage());
                return "error-page";
            }
        }
    }
}
