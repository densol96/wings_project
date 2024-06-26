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
import lv.wings.model.Piegades_veids;
import lv.wings.service.IPiegadesVeidsService;


@Controller
@RequestMapping("/piegades/veids")
public class PiegadesVeidsController {
    
    @Autowired
    private IPiegadesVeidsService pvService;


    @GetMapping("/show/all")
    public String getShowAllPV(Model model){
        try {
            ArrayList<Piegades_veids> allpv = pvService.selectAllPiegadesVeids();
            model.addAttribute("mydata", allpv);
            return "pv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOnePV(@PathVariable("id") int id, Model model){
        try {
            Piegades_veids pv = pvService.selectPiegadesVeidsById(id);
            model.addAttribute("mydata", pv);
            return "pv-one-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{id}")
    public String getDeleteOnePV(@PathVariable("id") int id, Model model){
        try {
            pvService.deletePiegadesVeidsById(id);
            ArrayList<Piegades_veids> allpv = pvService.selectAllPiegadesVeids();
            model.addAttribute("mydata", allpv);
            return "pv-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddPV(Model model) {
        model.addAttribute("pv", new Piegades_veids());
        return "pv-add-page";
    }


    @PostMapping("/add")
    public String postAddPV(@Valid Piegades_veids pv, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pv-add-page";
        } else {
            try {
                pvService.insertNewPiegadesVeids(pv);
                return "redirect:/piegades/veids/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }

    
    @GetMapping("/update/{id}")
    public String getUpdatePV(@PathVariable("id") int id, Model model) {
        try {
            Piegades_veids pvToUpdate = pvService.selectPiegadesVeidsById(id);
            model.addAttribute("pv", pvToUpdate);
            model.addAttribute("id", id);
            return "pv-update-page";
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @PostMapping("/update/{id}")
    public String postUpdatePV(@PathVariable("id") int id, @Valid Piegades_veids pv, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "pv-update-page";
        } else {
            try {
                pvService.updatePiegadesVeidsById(id, pv);
                return "redirect:/piegades/veids/show/all/" + id;
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }
}
