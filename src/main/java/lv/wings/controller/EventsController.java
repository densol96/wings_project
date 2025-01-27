package lv.wings.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.wings.dto.DTOMapper;
import lv.wings.dto.object.EventDTO;
import lv.wings.exceptions.NoContentException;
import lv.wings.model.Event;
import lv.wings.model.EventPicture;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping(value = "/api/events")
public class EventsController {

	@Autowired
	private ICRUDService<Event> eventsService;

	@Autowired
	ICRUDService<EventPicture> eventsPictureService;

	@PostMapping(value = "/test")
	public ResponseEntity<ApiResponse<?>> testtt(@Valid @RequestBody EventDTO eventDTO,
			BindingResult result) {

		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());

			ApiResponse<List<String>> errorResponse = new ApiResponse<>("Nepareizi ievades lauki!", errors);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse<>("Jaunuma kategorija izveidota!", null));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ApiResponse<>("Kļūda: " + e.getMessage(), null));
		}

	}

	@GetMapping(value = "")
	public ResponseEntity<ApiResponse<?>> getAllEvents(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "startDate") String sort) {

							System.out.println(page - 1);
		try {
			Pageable pageable = PageRequest.of(page - 1, 10).withSort(Sort.by(sort));
			Page<Event> allEvents = eventsService.retrieveAll(pageable);

			
			
			ArrayList<EventDTO> eventsDTO = DTOMapper.mapMany(EventDTO.class, allEvents.toList().toArray(),
					new String[] { "eventCategory.events" });
			

			Page<Event> evts = new PageImpl(eventsDTO, pageable, allEvents.getTotalElements());
			

			return ResponseEntity.ok(new ApiResponse<>(null, evts));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(
					new ApiResponse<>(e.getMessage(), null));
		}

	}

	/*
	 * @GetMapping(value = "/show/{id}")
	 * public ResponseEntity<ApiResponse<Event>> getSingleNews(@PathVariable("id")
	 * int id) {
	 * 
	 * try {
	 * return ResponseEntity.ok(new ApiResponse<>(null,
	 * eventsService.retrieveById(id)));
	 * } catch (NoContentException e) {
	 * return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
	 * } catch (Exception e) {
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 * }
	 * }
	 */
}

/*
 * package lv.wings.controller;
 * 
 * import java.util.ArrayList;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Controller;
 * import org.springframework.ui.Model;
 * import org.springframework.validation.BindingResult;
 * import org.springframework.web.bind.annotation.GetMapping;
 * import org.springframework.web.bind.annotation.PathVariable;
 * import org.springframework.web.bind.annotation.PostMapping;
 * import org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import jakarta.validation.Valid;
 * import lv.wings.model.PasakumaKomentars;
 * import lv.wings.model.Pasakums;
 * import lv.wings.service.IPasakumaKomentarsService;
 * import lv.wings.service.IPasakumiService;
 * 
 * @Controller
 * 
 * @RequestMapping("/jaunumi")
 * public class EventsController {
 * 
 * 
 * @Autowired
 * private IPasakumiService pasakumsRepo;
 * 
 * @Autowired
 * private IPasakumaKomentarsService pasakumaKomentarsRepo;
 * 
 * @GetMapping("")
 * public String eventsPageDefault(Model model) {
 * 
 * try {
 * ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumi();
 * model.addAttribute("events", allPasakumi);
 * return "events-page";
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * }
 * 
 * @GetMapping("/{id}")
 * public String getEventById(@PathVariable("id") int id, Model model) {
 * 
 * try {
 * Pasakums event = pasakumsRepo.selectPaskumsById(id);
 * model.addAttribute("pasakumaKomentars", new PasakumaKomentars());
 * model.addAttribute("id", id);
 * model.addAttribute("event", event);
 * return "event-page";
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * }
 * 
 * @PostMapping("/{id}")
 * public String postPasakumaKomentarsInsert(@PathVariable("id") int pasakumsId,
 * 
 * @Valid PasakumaKomentars pasakumaKomentars, BindingResult result, Model
 * model) throws Exception {
 * if (result.hasErrors()) {
 * Pasakums event = pasakumsRepo.selectPaskumsById(pasakumsId);
 * model.addAttribute("pasakumaKomentars", new PasakumaKomentars());
 * model.addAttribute("id", pasakumsId);
 * model.addAttribute("event", event);
 * return "event-page";
 * }
 * 
 * try {
 * Pasakums pasakums = pasakumsRepo.selectPaskumsById(pasakumsId);
 * pasakumaKomentars.setPasakums(pasakums);
 * pasakumaKomentarsRepo.create(pasakumaKomentars);
 * return "redirect:/jaunumi/" + pasakumsId;
 * 
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * }
 * 
 * @PostMapping("/delete/{id}")
 * public String postEventRemoveById(@PathVariable("id") int id, Model model) {
 * 
 * try {
 * pasakumsRepo.deletePasakumiById(id);
 * return "redirect:/jaunumi";
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * }
 * 
 * @GetMapping("/sort/{sortType}")
 * public String eventsSortDesc(@PathVariable("sortType") String sortType, Model
 * model) {
 * 
 * try {
 * if (sortType.equals("asc")) {
 * ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumiAscOrder();
 * model.addAttribute("events", allPasakumi);
 * } else if (sortType.equals("desc")) {
 * ArrayList<Pasakums> allPasakumi = pasakumsRepo.selectAllPasakumiDescOrder();
 * model.addAttribute("events", allPasakumi);
 * 
 * }
 * 
 * return "events-page";
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * }
 * 
 * 
 * 
 * @GetMapping("/add")
 * public String getPasakumsInsert(Model model) {
 * model.addAttribute("pasakums", new Pasakums());
 * return "event-add-page";
 * }
 * 
 * @PostMapping("/add")
 * public String postPasakumsInsert(@Valid Pasakums pasakums, BindingResult
 * result) throws Exception {
 * 
 * if (result.hasErrors()) {
 * return "event-add-page";
 * } else {
 * pasakumsRepo.create(pasakums);
 * return "redirect:/jaunumi";
 * }
 * }
 * 
 * 
 * @GetMapping("/update/{id}")
 * public String getPasakumsUpdateById(@PathVariable("id") int id, Model model)
 * {
 * try {
 * Pasakums pasakumsForUpdate = pasakumsRepo.selectPaskumsById(id);
 * model.addAttribute("pasakums", pasakumsForUpdate);
 * model.addAttribute("id", id);
 * return "event-update-page";
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * }
 * 
 * @PostMapping("/update/{id}")
 * public String postPasakumsUpdateById(@PathVariable("id") int id,@Valid
 * Pasakums pasakums, BindingResult result, Model model) {
 * try {
 * if(result.hasErrors()) {
 * return "event-update-page";
 * }
 * pasakumsRepo.update(id,pasakums);
 * return "redirect:/jaunumi";
 * } catch (Exception e) {
 * model.addAttribute("message", e.getMessage());
 * return "error-page";
 * }
 * 
 * 
 * }
 * 
 * }
 */
