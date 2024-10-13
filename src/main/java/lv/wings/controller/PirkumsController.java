package lv.wings.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
import lv.wings.model.Piegades_veids;
import lv.wings.model.Pircejs;
import lv.wings.model.Pirkums;
import lv.wings.model.Samaksas_veids;
import lv.wings.poi.PoiController;
import lv.wings.service.IPiegadesVeidsService;
import lv.wings.service.IPircejsService;
import lv.wings.service.IPirkumsService;
import lv.wings.service.ISamaksasVeidsService;


@Controller
@RequestMapping("/pirkums")
public class PirkumsController {
    
    @Autowired
    private IPirkumsService pirkumsService;

    @Autowired
    private IPiegadesVeidsService pvService;

    @Autowired
    private ISamaksasVeidsService svService;

    @Autowired
    private IPircejsService pircejsService;


    @GetMapping("/show/all")
    public String getShowAllPirkums(Model model){
        try {
            ArrayList<Pirkums> allpirkums = pirkumsService.selectAllPirkums();
            model.addAttribute("mydata", allpirkums);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/show/all/{id}")
    public String getShowOnePirkums(@PathVariable("id") int id, Model model){
        try {
            Pirkums pirkums = pirkumsService.selectPirkumsById(id);
            model.addAttribute("mydata", pirkums);
            return "pirkums-all-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/download/all/{id}")//localhost:8080/pirkums/download/all/{id}
	public ResponseEntity<byte[]> downloadPrecesById(@PathVariable("id") int id) {
		try {
			Pirkums pirkums = pirkumsService.selectPirkumsById(id);
			//iegūt faila baitus no preces ar definētiem laukiem
			byte[] fileBytes = PoiController.buildInvoice("pirkums-"+id, pirkums);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "invoice-"+id+".docx");

			return ResponseEntity.ok().headers(headers).body(fileBytes);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
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
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAddPirkums(Model model) {
        try {
            ArrayList<Samaksas_veids> samaksasVeidi = svService.selectAllSamaksasVeids();
            ArrayList<Piegades_veids> piegadesVeidi = pvService.selectAllPiegadesVeids();
            ArrayList<Pircejs> pirceji = pircejsService.selectAllPircejs();
            model.addAttribute("pirkums", new Pirkums());
            model.addAttribute("samaksasVeidi", samaksasVeidi);
            model.addAttribute("piegadesVeidi", piegadesVeidi);
            model.addAttribute("pirceji", pirceji);
            model.addAttribute("pasutijumaDatums", LocalDateTime.now());
            return "pirkums-add-page";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }


    @PostMapping("/add")
    public String postAddPirkums(@Valid Pirkums pirkums, BindingResult result, Model model) {
        if(result.hasErrors()) {
            System.out.println(pirkums.getPiegadesVeids());
            System.out.println(pirkums.getSamaksasVeids());
            System.out.println(pirkums.getPircejs());
            System.out.println(pirkums.getPiegadesDetalas());
            System.out.println(pirkums.getPasutijumaDatums());
            return "pirkums-add-page";
        } else {
            try {
                System.out.println("aaaab");
                pirkumsService.insertNewPirkums(pirkums);
                return "redirect:/pirkums/show/all";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error-page";
            }
        }
    }
}

