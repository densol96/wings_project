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
import lv.wings.model.Pirkums;
import lv.wings.service.IPirkumsService;


@Controller
@RequestMapping("/pirkums")
public class PirkumsController {
    
    @Autowired
    private IPirkumsService pirkumsService;


    @GetMapping("/show/all")
    public String getShowAllPirkums(Model model){
        try {
            ArrayList<Pirkums> allpirkums = pirkumsService.selectAllPirkums();
            model.addAttribute("mydata", allpirkums);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOnePirkums(@PathVariable("id") int id, Model model){
        try {
            Pirkums pirkums = pirkumsService.selectPirkumsById(id);
            model.addAttribute("mydata", pirkums);
            return "pirkums-one-page";
        } catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{id}")
    public String getDeleteOnePirkums(@PathVariable("id") int id, Model model){
        try {
            pirkumsService.deletePirkumsById(id);
            ArrayList<Pirkums> allpirkums = pirkumsService.selectAllPirkums();
            model.addAttribute("mydata", allpirkums);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("mydata", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddPirkums(Model model) {
        model.addAttribute("pirkums", new Pirkums());
        return "pirkums-add-page";
    }


    @PostMapping("/add")
    public String postAddPirkums(@Valid Pirkums pirkums, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pirkums-add-page";
        } else {
            try {
                pirkumsService.insertNewPirkums(pirkums);
                return "redirect:/pirkums/show/all";
            } catch (Exception e) {
                model.addAttribute("mydata", e.getMessage());
                return "error-page";
            }
        }
    }
}

