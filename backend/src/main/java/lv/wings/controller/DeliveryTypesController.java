package lv.wings.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.enums.Country;
import lv.wings.service.DeliveryTypeService;


@RestController
@RequestMapping(value = "/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryTypesController {

    private final DeliveryTypeService deliveryTypeService;

    @GetMapping("/per-country/{countryCode}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesPerCountry(@PathVariable Country countryCode) {
        return ResponseEntity.ok().body(deliveryTypeService.getDeliveryMethodsPerCountry(countryCode));
    }

}
