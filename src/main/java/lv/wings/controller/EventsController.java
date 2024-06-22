package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.wings.model.PasakumaBilde;
import lv.wings.model.Pasakums;
import lv.wings.service.IPasakumaBildeService;
import lv.wings.service.IPasakumiService;

@Controller
@RequestMapping("/jaunumi")
public class EventsController {
	@Autowired
	private IPasakumiService pasakumsRepo;

	@GetMapping("")
	public String eventsPageDefault(Model model) {

		try {
			// ArrayList<Pasakums> allPasakumi = pasakumiRepo.selectAllPasakumi();
			ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumi();

			model.addAttribute("events", allPasakumi);

			return "events-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}

	@GetMapping("/sort/{sortType}")
	public String eventsSortDesc(@PathVariable("sortType") String sortType, Model model) {

		try {
			if (sortType.equals("asc")) {
				ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumiAscOrder();
				model.addAttribute("events", allPasakumi);
			} else if (sortType.equals("desc")) {
				ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumiDescOrder();
				model.addAttribute("events", allPasakumi);

			}

			return "events-page";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error-page";
		}

	}

	

	/*
	 * @GetMapping("/jaunumi") public String eventsPage(Model model) {
	 * 
	 * try { ArrayList<Pasakums> allPasakumi = pasakumiRepo.selectAllPasakumi();
	 * model.addAttribute("events", allPasakumi);
	 * 
	 * return "events-page"; } catch (Exception e) { model.addAttribute("message",
	 * e.getMessage()); return "error-page"; }
	 * 
	 * }
	 */

}
