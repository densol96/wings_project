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
import lv.wings.model.Samaksas_veids;
import lv.wings.service.ISamaksasVeidsService;

@Controller
@RequestMapping("/samaksas/veids")
public class SamaksasVeidsController {
    
    @Autowired
    private ISamaksasVeidsService svService;


    @GetMapping("/show/all")
    public String getShowAllSV(Model model){
        try {
            ArrayList<Samaksas_veids> allsv = svService.selectAllSamaksasVeids();
            model.addAttribute("mydata", allsv);
            return "sv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOneSV(@PathVariable("id") int id, Model model){
        try {
            Samaksas_veids sv = svService.selectSamaksasVeidsById(id);
            model.addAttribute("mydata", sv);
            return "sv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{id}")
    public String getDeleteOneSV(@PathVariable("id") int id, Model model){
        try {
            svService.deleteSamaksasVeidsById(id);
            ArrayList<Samaksas_veids> allsv = svService.selectAllSamaksasVeids();
            model.addAttribute("mydata", allsv);
            return "sv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddSV(Model model) {
        model.addAttribute("sv", new Samaksas_veids());
        return "sv-add-page";
    }


    @PostMapping("/add")
    public String postAddSV(@Valid Samaksas_veids sv, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "sv-add-page";
        } else {
            try {
                svService.insertSamaksasVeids(sv);
                return "redirect:/samaksas/veids/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }

    
    @GetMapping("/update/{id}")
    public String getUpdateSV(@PathVariable("id") int id, Model model) {
        try {
            Samaksas_veids svToUpdate = svService.selectSamaksasVeidsById(id);
            model.addAttribute("sv", svToUpdate);
            model.addAttribute("id", id);
            return "sv-update-page";
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @PostMapping("/update/{id}")
    public String postUpdateSV(@PathVariable("id") int id, @Valid Samaksas_veids sv, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "sv-update-page";
        } else {
            try {
                svService.updateSamaksasVeidsById(id, sv);
                return "redirect:/samaksas/veids/show/all/" + id;
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }


}
