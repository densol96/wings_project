package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.wings.exceptions.NoContentException;
import lv.wings.model.DeliveryType;
import lv.wings.responses.ApiArrayListResponse;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping(value = "/api/deliverytypes")
public class DeliveryTypesController {
    
    @Autowired
    private ICRUDService<DeliveryType> deliveryTypeService;

    @GetMapping(value = "/show/all")
	public ResponseEntity<ApiArrayListResponse<DeliveryType>> getAllDeliveryTypes() {

		try {
			ArrayList<DeliveryType> allDeliveryTypes = deliveryTypeService.retrieveAll();

			return ResponseEntity.ok(new ApiArrayListResponse<>(null, allDeliveryTypes));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiArrayListResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping(value = "/show/{id}")
	public ResponseEntity<ApiResponse<DeliveryType>> getSingleDeliveryType(@PathVariable("id") int id) {

		try {
			return ResponseEntity.ok(new ApiResponse<>(null, deliveryTypeService.retrieveById(id)));
		} catch (NoContentException e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
